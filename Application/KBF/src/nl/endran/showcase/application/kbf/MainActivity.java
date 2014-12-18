package nl.endran.showcase.application.kbf;

import nl.endran.activity.EndranTracking;
import nl.endran.logging.LogLevel;
import nl.endran.logging.LoggerFactory;
import nl.endran.showcase.application.kbf.MainActivityView.MainActivityViewListener;
import nl.endran.showcase.application.kbf.fragments.presenter.AllBrewersFragment;
import nl.endran.showcase.application.kbf.fragments.presenter.AllFavoriteSummariesFragment;
import nl.endran.showcase.application.kbf.fragments.presenter.AllSummariesFragment;
import nl.endran.showcase.application.kbf.fragments.presenter.AllWishListSummariesFragment;
import nl.endran.showcase.application.kbf.menu.MainMenu;
import nl.endran.showcase.application.kbf.menu.MainMenu.MainMenuListener;
import nl.endran.showcase.application.kbf.menu.ShowCaseMenu;
import nl.endran.showcaselib.ShowcaseLibrary;
import nl.endran.showcaselib.datacontainers.CustomServerCredentials;
import nl.endran.showcaselib.datacontainers.FilterOptions;
import nl.endran.showcaselib.datacontainers.SortOptions;
import nl.endran.showcaselib.usecases.backend.SynchronizeCommand.SynchronizeAllListener;
import nl.endran.showcaselib.usecases.backend.TryLoginCommand.TryLoginCommandListener;
import nl.endran.showcaselib.usecases.backend.UploadUserDataCommand.UploadUserDataCommandListener;
import nl.endran.showcaselib.usecases.data.GetUserCredentials.UserCredentialsListener;
import nl.endran.showcaselib.usecases.data.IsInitializedCommand.IsInitializedListener;
import nl.endran.showcaseui.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends BaseShowcaseActivity {

    private static final String CURRENT_ITEM = "CURRENT_ITEM";
    private static final int DEFAULT_CURRENT_ITEM = 0;
    private int currentItem = DEFAULT_CURRENT_ITEM;

    private MainActivityView mainActivityView;
    private ShowCaseMenu mainMenu;

    private final SparseArray<Pair<Fragment, String>> fragmentArray = new SparseArray<Pair<Fragment, String>>();

    private boolean initialized;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoggerFactory.GetLogger(this);

        if (savedInstanceState != null) {
            currentItem = savedInstanceState.getInt(CURRENT_ITEM, DEFAULT_CURRENT_ITEM);
        }

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putBoolean(EndranTracking.ALLOW_STAT_KEY, true).commit();

        initializeFragments();

        mainActivityView = new MainActivityView(this, new MainActivityViewListener() {

            @Override
            public void onSearchEntered(final String searchString, final FilterOptions filterOptions, final SortOptions sortOptions) {
                final Intent intent = new Intent(MainActivity.this, AdvancedSearchActivity.class);
                intent.putExtra(AdvancedSearchActivity.SEARCH_INPUT, (searchString == null ? "" : searchString));
                intent.putExtra(AdvancedSearchActivity.FILTER_INPUT, filterOptions.getValue());
                intent.putExtra(AdvancedSearchActivity.SORT_INPUT, sortOptions.getValue());

                MainActivity.this.startActivity(intent);
            }
        });

        getMainMenu();
    }

    private ShowCaseMenu getMainMenu() {
        if (mainMenu == null) {
            mainMenu = new MainMenu(this, new MainMenuListener() {

                @Override
                public boolean onSettingsClicked() {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public boolean onRateClicked() {

                    final String appPackageName = getPackageName(); // getPackageName()
                                                                    // from
                                                                    // Context
                                                                    // or
                                                                    // Activity
                                                                    // object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (final android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                    }

                    return true;
                }

                @Override
                public boolean onSearchClicked() {
                    mainActivityView.showSearchDialog();
                    return true;
                }

                @Override
                public boolean onAboutClicked() {
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    return true;
                }

                @Override
                public boolean onAccountClicked() {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    return true;
                }
            });
        }
        return mainMenu;
    }

    @Override
    protected void initializeLogger() {
        LoggerFactory.setLogLevel(LogLevel.VERBOSE);
        LoggerFactory.setLogTag(getString(R.string.app_name));
        LoggerFactory.setLogLevelForClass("XmlParser", LogLevel.ERROR);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMainMenu().createOptionsMenu(menu, getMenuInflater());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final boolean result = mainMenu.optionsItemSelected(item);

        if (result) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle savedInstanceState) {
        if (mainActivityView != null) {
            currentItem = mainActivityView.getCurrentItem();
            savedInstanceState.putInt(CURRENT_ITEM, currentItem);
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    void onShowcaseServiceConnected(final ShowcaseLibrary showcaseLibrary) {
        waitForInitialized();

        getUserCredentials();
    }

    private void waitForInitialized() {
        showcaseLibrary.getDataAccess().isInitialized(new IsInitializedListener() {

            @Override
            public void onFail(final String message) {
                MainActivity.this.log.error("IsInitializedListener onFail: " + message);
            }

            @Override
            public void onIsInitialized(final boolean isInitialized) {
                initialized = isInitialized;

                if (initialized) {
                    mainActivityView.show(fragmentArray, currentItem);
                }
            }
        });
    }

    @Override
    protected void syncAll() {
        showcaseLibrary.getBackendAccess().synchronizeAll(new SynchronizeAllListener() {

            @Override
            public void onFail(final String message) {
                log.error("SynchronizeAllListener onFail: " + message);
                mainActivityView.showError(R.string.could_not_dowload_database);
            }

            @Override
            public void onSynced() {
                log.info("SynchronizeAllListener onSynced");
                if (!initialized) {
                    mainActivityView.show(fragmentArray, currentItem);
                }
            }
        });
    }

    private void getUserCredentials() {
        showcaseLibrary.getDataAccess().getUserCredentials(new UserCredentialsListener() {

            @Override
            public void onFail(final String message) {
                mainActivityView.showError(message);
            }

            @Override
            public void onCredentials(final CustomServerCredentials customServerCredentials) {
                ApplicationState.activated = customServerCredentials.isActivated();

                if (customServerCredentials.getUserId() > 0) {
                    ApplicationState.userId = customServerCredentials.getUserId();
                } else {
                    if (ApplicationState.activated) {
                        tryLogin(customServerCredentials);
                    }

                    ApplicationState.userId = -1;
                }
            }

            private void tryLogin(final CustomServerCredentials customServerCredentials) {
                showcaseLibrary.getBackendAccess().tryLogin(customServerCredentials.isActivated(), customServerCredentials.getUserName(),
                        customServerCredentials.getEmail(), new TryLoginCommandListener() {
                            @Override
                            public void onFail(final String message) {
                            }

                            @Override
                            public void onCustomServerCredentials(final CustomServerCredentials customServerCredentials) {
                                if (customServerCredentials.getUserId() > 0) {
                                    ApplicationState.userId = customServerCredentials.getUserId();
                                    mainActivityView.showMessage(R.string.login_toast_loggedin);

                                    showcaseLibrary.getBackendAccess().uploadUserDataCommand(new UploadUserDataCommandListener() {

                                        @Override
                                        public void onFail(final String message) {
                                            // TODO Auto-generated method stub

                                        }

                                        @Override
                                        public void onUpdated() {
                                            mainActivityView.showMessage(R.string.login_toast_data_backupped);
                                        }

                                        @Override
                                        public void onNotUpdated() {
                                            // TODO Auto-generated method stub

                                        }
                                    });
                                }
                            }
                        });
            }
        });
    }

    @Override
    void onShowcaseServiceDisconnected() {
    }

    private void initializeFragments() {

        fragmentArray.clear();

        int id = 0;
        // fragmentArray.put(id++, new Pair<Fragment, String>(new
        // FestivalFragment(), getString(R.string.festivalFragmentName)));
        fragmentArray.put(id++, new Pair<Fragment, String>(new AllSummariesFragment(), getString(R.string.allSummariesFragmentName)));
        fragmentArray.put(id++, new Pair<Fragment, String>(new AllBrewersFragment(), getString(R.string.allBrewersFragmentName)));
        fragmentArray.put(id++, new Pair<Fragment, String>(new AllFavoriteSummariesFragment(), getString(R.string.allFavoriteSummariesFragmentName)));
        fragmentArray.put(id++, new Pair<Fragment, String>(new AllWishListSummariesFragment(), getString(R.string.allWishListSummariesFragmentName)));
    }
}
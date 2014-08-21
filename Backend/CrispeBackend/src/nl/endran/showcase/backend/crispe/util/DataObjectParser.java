package nl.endran.showcase.backend.crispe.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import nl.endran.showcase.backend.crispe.extendeddatacontainers.IArrayOf;
import nl.endran.showcase.backend.crispe.extendeddatacontainers.XmlParser;
import nl.endran.showcasebackend.R;
import nl.endran.showcaselib.datacontainers.ShowcaseDataObject;
import nl.endran.storage.Storage;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.SparseArray;

public class DataObjectParser {
	ILogger log = LoggerFactory.GetLogger(this);

	private final Context context;

	boolean completeSyncOfDatabase;

	public DataObjectParser(final Context context, final boolean completeSyncOfDatabase) {
		this.context = context;

		if (completeSyncOfDatabase) {
			loadDefaultContents(context);
		}
	}

	/**
	 * Load some default resources, which are not transferred from the backend.
	 * 
	 * @param context
	 */
	private void loadDefaultContents(final Context context) {
		final Storage storage = new Storage(context);

		final SparseArray<String> defaultContents = new SparseArray<String>();
		defaultContents.put(R.raw.methods, "methods");
		defaultContents.put(R.raw.payments, "payments");
		defaultContents.put(R.raw.stocks, "stocks");

		for (int i = 0; i < defaultContents.size(); i++) {
			final int rawId = defaultContents.keyAt(i);
			final InputStream fileInputStream = context.getResources().openRawResource(rawId);
			final String filename = defaultContents.get(rawId) + ".xml";
			storage.deleteFile(filename);
			final FileOutputStream fileOutputStream = storage.getFileOutputStream(filename);
			storage.copy(fileInputStream, fileOutputStream);
			try {
				fileInputStream.close();
			} catch (final IOException ex) {
				log.error("IOException", ex);
			}
			try {
				fileOutputStream.close();
			} catch (final IOException ex) {
				log.error("IOException", ex);
			}
		}
	}

	/**
	 * Parse whatever files there are available in the storage, as long as the end with .xml.
	 * 
	 * @return
	 */
	public List<ArrayList<ShowcaseDataObject>> parse() {
		log.verbose("parse");
		final Storage storage = new Storage(context);

		boolean succes = false;
		final String[] files = storage.getFileArray();
		final ArrayList<IArrayOf<ShowcaseDataObject>> parsedObjectList = new ArrayList<IArrayOf<ShowcaseDataObject>>();

		try {
			for (final String file : files) {
				if (!file.endsWith(".xml")) {
					continue;
				}
				log.verbose("parse Start parsing file " + file);

				final IArrayOf<ShowcaseDataObject> parsedObject = new XmlParser().deserialize(storage.getFileInputStream(file));
				if (parsedObject != null) {
					parsedObjectList.add(parsedObject);
				} else {
					log.error("parse deserialize returned null");
				}
				log.verbose("Finsihed with " + file);
				storage.deleteFile(file);
			}

			succes = true;
		} catch (final XmlPullParserException e) {
			log.error("An error occurred during parsing:XmlPullParserException", e);
		} catch (final IOException e) {
			log.error("An error occurred during parsing:IOException", e);
		} catch (final ClassNotFoundException e) {
			log.error("An error occurred during parsing:ClassNotFoundException", e);
		} catch (final IllegalArgumentException e) {
			log.error("An error occurred during parsing:IllegalArgumentException", e);
		} catch (final IllegalAccessException e) {
			log.error("An error occurred during parsing:IllegalAccessException", e);
		} catch (final InstantiationException e) {
			log.error("An error occurred during parsing:InstantiationException", e);
		} catch (final InvocationTargetException e) {
			log.error("An error occurred during parsing:InvocationTargetException", e);
		}

		log.verbose("parse finished");

		if (!succes) {
			return null;
		}

		return formatParsedObject(parsedObjectList);
	}

	private List<ArrayList<ShowcaseDataObject>> formatParsedObject(final List<IArrayOf<ShowcaseDataObject>> parsedObjectList) {
		final List<ArrayList<ShowcaseDataObject>> showcaseDataObjectList = new ArrayList<ArrayList<ShowcaseDataObject>>();
		for (final IArrayOf<ShowcaseDataObject> arrayOf : parsedObjectList) {
			showcaseDataObjectList.add(arrayOf.getList());
		}
		return showcaseDataObjectList;
	}
}

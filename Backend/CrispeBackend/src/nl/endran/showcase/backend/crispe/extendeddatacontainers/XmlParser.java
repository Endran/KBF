package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

import android.annotation.SuppressLint;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import nl.endran.showcaselib.datacontainers.ShowcaseDataObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

@SuppressLint("DefaultLocale")
public class XmlParser {
	private final ILogger log = LoggerFactory.GetLogger(this);
	private final String packageName;

	private final Stack<ClassHolder> classStack = new Stack<ClassHolder>();

	public XmlParser() {
		this.packageName = this.getClass().getPackage().getName();
	}

	@SuppressWarnings("unchecked")
	public IArrayOf<ShowcaseDataObject> deserialize(final InputStream in) throws XmlPullParserException, IOException, ClassNotFoundException,
			IllegalAccessException, InstantiationException, IllegalArgumentException, InvocationTargetException {
		final XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		final XmlPullParser xpp = factory.newPullParser();
		xpp.setInput(new InputStreamReader(in));

		int eventType = xpp.getEventType();
		String parameterValue = null;
		String parameterName = null;

		boolean primitiveFlag = false;

		// Stop when end is reached
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				log.verbose("Start document");
				break;
			case XmlPullParser.END_DOCUMENT:
				log.verbose("End document");
				break;
			case XmlPullParser.START_TAG:
				final String startTag = xpp.getName();
				log.verbose("Start tag: " + startTag);

				if (startTag != null) {
					try {
						// Try to find a class that has a name like the
						// startTag within this package. When found, push it to the stack
						final String className = this.packageName + "." + startTag;
						final Class<?> clazz = Class.forName(className);
						classStack.push(new ClassHolder(clazz));
						primitiveFlag = false;
					} catch (final ClassNotFoundException ex) {
						try {
							// Try to find a class that has a name like the
							// startTag within ShowcaseDataObject package. When found, push it to the stack
							final String className = ShowcaseDataObject.class.getPackage().getName() + "." + startTag;
							final Class<?> clazz = Class.forName(className);
							classStack.push(new ClassHolder(clazz));
							primitiveFlag = false;
						} catch (final ClassNotFoundException ex2) {
							// Not found, so its either a primitive or an error.
							// Just go with primitive, because if its an error we
							// will detect it later
							primitiveFlag = true;
							parameterName = startTag;
						}
					}
				}

				// Any value that is still remaining is to be flushed.
				parameterValue = null;

				break;
			case XmlPullParser.END_TAG:
				log.verbose("End tag: " + xpp.getName());

				// Primitives also have end tags, so we need to skip any class
				// related end tag stuff
				if (primitiveFlag) {
					primitiveFlag = false;
					break;
				}

				// We pop the top class, and try to call the setter of the
				// underlying class. If there is no underlying class, than we
				// have reached the root and we can return this root
				final ClassHolder completedObject = classStack.pop();
				if (classStack.size() > 0) {
					final ClassHolder currentObject = classStack.peek();

					log.verbose("For object " + currentObject.targetName + " calling method reflectionSet" + completedObject.targetName + " with parameter "
							+ completedObject.targetName);
					callMethodForName(currentObject.instance, currentObject.methods, completedObject.targetName, completedObject.instance);
				} else {
					// Return the completed object
					return (IArrayOf<ShowcaseDataObject>) completedObject.instance;
				}
				break;
			case XmlPullParser.TEXT:
				parameterValue = xpp.getText();
				log.verbose("Text: " + parameterValue);
				break;
			default:
				break;
			}

			if (parameterName != null && !parameterName.equals("") && parameterValue != null) {
				final ClassHolder classHolder = classStack.peek();
				log.verbose("For object " + classHolder.targetName + " calling method reflectionSet" + parameterName + " with parameter " + parameterValue);
				callMethodForName(classHolder.instance, classHolder.methods, parameterName, parameterValue.trim());
				parameterName = null;
				parameterValue = null;
			}

			eventType = xpp.next();
		}

		return null;

	}

	@SuppressLint("DefaultLocale")
	private void callMethodForName(final Object receiver, final Method[] methods, final String name, final Object arg) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		final String setter = ("set" + name).toLowerCase();
		for (final Method method : methods) {
			if (method.getName().toLowerCase().equals(setter)) {
				method.setAccessible(true);
				final Class<?>[] parameterType = method.getParameterTypes();

				if (parameterType[0].getName().equals("int")) {
					final int parsed = Integer.parseInt((String) arg);
					method.invoke(receiver, new Object[] { parsed });
				} else if (parameterType[0].getName().equals("boolean")) {
					final boolean parsed = Boolean.parseBoolean((String) arg);
					method.invoke(receiver, new Object[] { parsed });
				} else if (parameterType[0].getName().equals("float")) {
					final float parsed = Float.parseFloat((String) arg);
					method.invoke(receiver, new Object[] { parsed });
				} else if (parameterType[0].getName().equals("double")) {
					final double parsed = Double.parseDouble((String) arg);
					method.invoke(receiver, new Object[] { parsed });
				} else if (parameterType[0].getName().equals("byte")) {
					final byte parsed = Byte.parseByte((String) arg);
					method.invoke(receiver, new Object[] { parsed });
				} else if (parameterType[0].getName().equals("short")) {
					final short parsed = Short.parseShort((String) arg);
					method.invoke(receiver, new Object[] { parsed });
				} else if (parameterType[0].getName().equals("long")) {
					final long parsed = Long.parseLong((String) arg);
					method.invoke(receiver, new Object[] { parsed });
				} else {
					method.invoke(receiver, new Object[] { arg });
				}

				return;
			}
		}

		log.warn("Could not find method " + setter);
	}

	private class ClassHolder {
		public final Method[] methods;
		public final String targetName;
		public Object instance;

		public ClassHolder(final Class<?> clazz) {
			methods = clazz.getMethods();

			final String[] splitted = clazz.getName().split("\\.");
			targetName = splitted[splitted.length - 1];

			try {
				instance = clazz.newInstance();
			} catch (final InstantiationException e) {
				log.error("InstantiationException", e);
			} catch (final IllegalAccessException e) {
				log.error("IllegalAccessException", e);
			}
		}
	}
}
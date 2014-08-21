package nl.endran.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import nl.endran.logging.ILogger;
import nl.endran.logging.LoggerFactory;
import android.content.Context;

public class Storage {
	ILogger log = LoggerFactory.GetLogger(this);

	private Context context;

	public Storage(Context context) {
		this.context = context;
	}

	public String[] getFileArray() {
		return context.fileList();
	}

	public boolean exists(String filename) {
		boolean res = false;

		String[] allFiles = context.fileList();
		for (String file : allFiles) {
			res = file.equals(filename);
			if (res) {
				break;
			}
		}

		return res;
	}

	public boolean deleteFile(String filename) {
		return context.deleteFile(filename);
	}

	public FileInputStream getFileInputStream(String filename) {
		FileInputStream fis = null;
		try {
			fis = context.openFileInput(filename);
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException", e);
		}
		return fis;
	}

	public FileOutputStream getFileOutputStream(String filename) {
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException", e);
		}
		return fos;
	}

	public String read(String filename) {
		if (!exists(filename)) {
			return "";
		}

		char[] buffer = new char[256];
		InputStreamReader reader = null;
		StringBuilder sb = new StringBuilder();

		try {
			int length;
			reader = new InputStreamReader(context.openFileInput(filename));
			do {
				length = 0;
				length = reader.read(buffer, 0, 256);
				if (length > 0) {
					sb.append(new String(buffer, 0, length));
				}
			} while (length >= 256);
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException", e);
		} catch (IOException e) {
			log.error("IOException", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}
		return sb.toString();
	}

	public void copy(InputStream source, OutputStream destination) {
		byte[] buffer = new byte[256];
		int length = 0;
		try {
			do {
				length = source.read(buffer, 0, 256);
				if (length > 0) {
					destination.write(buffer, 0, length);
					destination.flush();
				}
			} while (length >= 256);
		} catch (IOException e) {
			log.error("IOException", e);
		}
	}

	public void copy(InputStreamReader source, OutputStreamWriter destination) {
		char[] buffer = new char[256];
		int length = 0;
		try {
			do {
				length = source.read(buffer, 0, 256);
				if (length > 0) {
					destination.write(buffer, 0, length);
					destination.flush();
				}
			} while (length >= 256);
		} catch (IOException e) {
			log.error("IOException", e);
		}
	}

	public void save(String filename, String contents) {
		OutputStreamWriter writer = null;
		;
		try {
			writer = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
			writer.write(contents);
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException", e);
		} catch (IOException e) {
			log.error("IOException1", e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				log.error("IOException2", e);
			}
		}
	}

	public void clearAll() {
		String[] files = getFileArray();
		for (String file : files) {
			deleteFile(file);
		}
	}
}

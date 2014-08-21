package nl.endran.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Convenience class for AES encryption. Convert byte arrays of clearText to
 * cypherText and vice versa.
 */
public class AesEncryption {
	/**
	 * Encrypt a message with symmetric encryption AES. The same key should be
	 * used to encrypt and decrypt this message. The key should only be know by
	 * whom it may concern.
	 * 
	 * @param key
	 *            Secret key used for encryption and decryption
	 * @param clearText
	 *            Message to encrypt
	 * @throws IllegalStateException
	 *             Several exceptions can occur, which are all bundled in this
	 *             exception. The message of this will be the message of the
	 *             causing exception. The original exception is set as the
	 *             casue. Possible exceptions are; NoSuchAlgorithmException,
	 *             NoSuchPaddingException, InvalidKeyException,
	 *             IllegalBlockSizeException and BadPaddingException.
	 */
	public static byte[] encrypt(final byte[] key, final byte[] clearText) throws IllegalStateException {
		final SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		byte[] cypherText = null;
		try {
			Cipher cipher;
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			cypherText = cipher.doFinal(clearText);
		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (final NoSuchPaddingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (final InvalidKeyException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (final IllegalBlockSizeException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (final BadPaddingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		return cypherText;
	}

	/**
	 * Decrypt a message with symmetric encryption AES. The same key should be
	 * used to decrypt and encrypt this message. The key should only be know by
	 * whom it may concern.
	 * 
	 * @param key
	 *            Secret key used for encryption and decryption
	 * @param clearText
	 *            Message to decrypt
	 * @throws IllegalStateException
	 *             Several exceptions can occur, which are all bundled in this
	 *             exception. The message of this will be the message of the
	 *             causing exception. The original exception is set as the
	 *             casue. Possible exceptions are; NoSuchAlgorithmException,
	 *             NoSuchPaddingException, InvalidKeyException,
	 *             IllegalBlockSizeException and BadPaddingException.
	 */
	public static byte[] decrypt(final byte[] key, final byte[] cypherText) throws IllegalStateException {
		final SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher;
		byte[] clearText;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			clearText = cipher.doFinal(cypherText);
		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (final NoSuchPaddingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (final InvalidKeyException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (final IllegalBlockSizeException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (final BadPaddingException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		return clearText;
	}
}

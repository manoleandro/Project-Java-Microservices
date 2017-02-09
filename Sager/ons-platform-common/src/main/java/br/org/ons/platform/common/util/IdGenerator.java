package br.org.ons.platform.common.util;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

/**
 * Classe utilitária para geração de IDs únicos
 * 
 * Gera strings únicas a partir de java.util.UUID codificados em Base64. As
 * strings geradas são URL-safe e podem ser utilizadas para identificar recursos
 * em uma API REST.
 */
public class IdGenerator {
	
	/**
	 * Comprimento em bytes do UUID
	 */
	private static final int UUID_BYTE_LENGTH = 16;

	/**
	 * Construtor privado para evitar instanciação da classe
	 */
	private IdGenerator() {
		
	}
	
	/**
	 * @return UUID codificado em Base64
	 */
	public static String newId() {
	    UUID uuid = UUID.randomUUID();
	    ByteBuffer bb = ByteBuffer.wrap(new byte[UUID_BYTE_LENGTH]);
	    bb.putLong(uuid.getMostSignificantBits());
	    bb.putLong(uuid.getLeastSignificantBits());
	    return Base64.encodeBase64URLSafeString(bb.array());
	}
}

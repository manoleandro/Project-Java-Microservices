package com.sager;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbJSON;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.misc.BASE64Encoder;

public class GeraIndAcum_Ambos2Base64JSON extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		MbMessage inMessage = inAssembly.getMessage();

		// create new empty message
		MbMessage outMessage = new MbMessage();
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,
				outMessage);

		try {
			// optionally copy message headers
			// copyMessageHeaders(inMessage, outMessage);
			// ----------------------------------------------------------
			// Add user code below
			BASE64Encoder encoder = new BASE64Encoder();
			String xmlBase64 = encoder.encode(inMessage.getRootElement().getFirstElementByPath("/XMLNSC").toBitstream(null, null, null, 0, 1208, 0)).replace("\r\n", "");
			
			MbElement env = inAssembly.getLocalEnvironment().getRootElement();
			MbElement dadosDat = env.getFirstElementByPath("Data/dadosDat");		
			
			MbElement outRoot = outMessage.getRootElement();
			MbElement outParser = outRoot.createElementAsLastChild(MbJSON.PARSER_NAME);
			
			MbElement outJsonData = outParser.createElementAsLastChild(MbElement.TYPE_NAME, MbJSON.DATA_ELEMENT_NAME, null);
			MbElement outJsonTest = outJsonData.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "arquivoXML", "INDACUM.XML");
			// outJsonData = outParser.createElementAsLastChild(MbElement.TYPE_NAME, MbJSON.DATA_ELEMENT_NAME, null);
			outJsonTest = outJsonData.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "dadosXML", xmlBase64);
			outJsonTest = outJsonData.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "arquivoDat", "INDACUM.DAT");
			outJsonTest = outJsonData.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "dadosDat", dadosDat.getValue());

			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(),
					null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);
	}

	public void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getRootElement();

		// iterate though the headers starting with the first child of the root
		// element
		MbElement header = inMessage.getRootElement().getFirstChild();
		while (header != null && header.getNextSibling() != null) // stop before
																	// the last
																	// child
																	// (body)
		{
			// copy the header and add it to the out message
			outRoot.addAsLastChild(header.copy());
			// move along to next header
			header = header.getNextSibling();
		}
	}

}

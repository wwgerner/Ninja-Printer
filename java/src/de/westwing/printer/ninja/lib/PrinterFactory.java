package de.westwing.printer.ninja.lib;

import javax.print.PrintService;

import de.westwing.printer.ninja.lib.message.JsonPrintMessageInterface;

/**
 * 
 * @author Omar Tchokhani <omar.tchokhani@westwing.de>
 *
 */
public class PrinterFactory {

	public enum PrinterType {
		LABEL("label"),
		PDF("PDF");
		
		private final String type;
		
		private PrinterType(String type) {
			this.type = type;
		}
		
		@Override public String toString() {
			return type;
		}
	}
	
	/**
	 * @param printerType
	 * @param printService
	 * @return A Concrete PrinterInterface object.
	 * @throws PrintException
	 */
	public static PrinterInterface factory(String printerType, PrintService printService) throws PrintException {
		if (PrinterType.LABEL.toString().equalsIgnoreCase(printerType)) {
			return new LabelPrinter(printService);
		}

		if (PrinterType.PDF.toString().equalsIgnoreCase(printerType)) {
			return new PdfPrinter(printService);
		}

		throw new PrintException("Printer Type not supported: " + printerType);
	}
	
	/**
	 * @param printMessage
	 * @return A Concrete PrinterInterface object.
	 * @throws PrintException
	 * @throws Exception
	 */
	public static PrinterInterface factory(JsonPrintMessageInterface printMessage) throws PrintException, Exception{
		PrintService printService = Utilities.lookupPrinterServiceByName(printMessage.getPrinterName());
		
		return factory(printMessage.getPrinterType(), printService);
	}
}

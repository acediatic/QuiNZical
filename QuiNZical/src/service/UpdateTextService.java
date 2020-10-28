package service;

import controller.PrimaryController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * A legacy class that allows the updating of text size with the resizing
 * of a window.
 * @author Adam and Osama
 *
 */
public class UpdateTextService extends Service<Void> {
	 private DoubleProperty _oldVal = new SimpleDoubleProperty();
	 private DoubleProperty _newVal = new SimpleDoubleProperty();
	
	 /**
	 * Sets the old and new text sizes, in px.
	 * @param oldVal, the old text size
	 * @param newVal, the new text size.
	 */
	public final void setDoubles(Number oldVal, Number newVal) {
		 _oldVal.set((Double) oldVal);
		 _newVal.set((Double) newVal);
     }

     /**
     * @return the old text size, in px.
     */
    public final Double getOldVal() {
         return _oldVal.get();
     }
     
     /**
     * @return the new text size, in px.
     */
    public final Double getNewVal() {
         return _newVal.get();
     }

     /**
     * Updates the root size to the new size, as a ratio.
     */
    protected Task<Void> createTask() {
         final Double oldVal = getOldVal();
         final Double newVal = getNewVal();
         return new Task<Void>() {
             protected Void call() {
            	 if(!oldVal.isNaN() && !newVal.isNaN()) {
         			double ratio = newVal.doubleValue() / oldVal.doubleValue();
         			PrimaryController.currentFontSize = PrimaryController.currentFontSize * ratio;
             }
            	 return null;
         };
     };
     }
}
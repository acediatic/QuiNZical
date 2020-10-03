package service;

import application.GameMainController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class UpdateTextService extends Service<Void> {
	 private DoubleProperty _oldVal = new SimpleDoubleProperty();
	 private DoubleProperty _newVal = new SimpleDoubleProperty();
	
	 public final void setDoubles(Number oldVal, Number newVal) {
		 _oldVal.set((Double) oldVal);
		 _newVal.set((Double) newVal);
     }

     public final Double getOldVal() {
         return _oldVal.get();
     }
     
     public final Double getNewVal() {
         return _newVal.get();
     }

     protected Task<Void> createTask() {
         final Double oldVal = getOldVal();
         final Double newVal = getNewVal();
         return new Task<Void>() {
             protected Void call() {
            	 if(!oldVal.isNaN() && !newVal.isNaN()) {
         			double ratio = newVal.doubleValue() / oldVal.doubleValue();
         			GameMainController._currentFontSize = GameMainController._currentFontSize * ratio;
             }
            	 return null;
         };
     };
     }
}
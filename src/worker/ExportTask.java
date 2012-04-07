package worker;

import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.SwingWorker;

import app.Log;
import app.Mediator;

public class ExportTask extends SwingWorker<Integer, Integer> {
	private static final int DELAY = 1000;
	PropertyChangeListener l;
	Mediator med;
	public ExportTask() {
		
	}

	@Override
	protected Integer doInBackground() throws Exception {
		Log.debug("doInBackground: "+Thread.currentThread());
		int count = 10;
		int i     = 0;
		try {
			while (i < count) {
				i++;
				Thread.sleep(DELAY);
				publish(i);
				setProgress(i);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 0;
	}

	protected void process(List<Integer> chunks) {
		Log.debug("process: " + " " + chunks);
	}

	@Override
	protected void done() {
		Log.debug("done: "+ Thread.currentThread());
		if (isCancelled())
			Log.debug("Cancelled !");
		else
			Log.debug("Done !");
	}
}
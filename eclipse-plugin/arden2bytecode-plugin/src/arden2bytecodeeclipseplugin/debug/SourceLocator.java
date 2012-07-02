package arden2bytecodeeclipseplugin.debug;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IPersistableSourceLocator;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;
import org.eclipse.jdt.debug.core.IJavaStackFrame;

import arden2bytecodeeclipseplugin.Activator;
import arden2bytecodeeclipseplugin.launchconfig.MainTab;

public class SourceLocator implements IPersistableSourceLocator {

	String project = null;
	
	public SourceLocator() {
		
	}
	
	@Override
	public Object getSourceElement(IStackFrame stackFrame) {
		if (stackFrame instanceof IJavaStackFrame) {
			IJavaStackFrame frame = (IJavaStackFrame) stackFrame;
			try {
				String sourceName = frame.getSourceName();
				if (sourceName != null) {
					File f = new File(sourceName);
					if (f.exists()) {
						return new LocalFileStorage(f);
					}
				}
			} catch (DebugException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public String getMemento() throws CoreException {
		return "<project>" + 
				(project != null ? project : "") + 
				"</project>";
	}

	@Override
	public void initializeFromMemento(String memento) throws CoreException {
		int begin, end;
		begin = memento.indexOf("<project>");
		end = memento.indexOf("</project>");
		if (begin >= 0 && end >= 0) {
			project = memento.substring(begin, end).trim();
		} else {
			throw new CoreException(new Status(Status.ERROR, 
						Activator.PLUGIN_ID, 
						"That memento has an error: " +
						memento));
		}
	}

	@Override
	public void initializeDefaults(ILaunchConfiguration configuration)
			throws CoreException {
		project = configuration.getAttribute(MainTab.PROJECT, MainTab.PROJECT_DEFAULT);
	}

}

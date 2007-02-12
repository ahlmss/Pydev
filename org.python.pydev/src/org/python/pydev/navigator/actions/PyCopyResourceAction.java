package org.python.pydev.navigator.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Shell;
import org.python.pydev.navigator.actions.copied.CopyAction;

public class PyCopyResourceAction extends CopyAction{

    private ISelectionProvider provider;

    private ArrayList<IResource> selected;

    public PyCopyResourceAction(Shell shell, ISelectionProvider selectionProvider, Clipboard clipboard) {
        super(shell, clipboard);
        this.provider = selectionProvider;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#isEnabled()
     */
    public boolean isEnabled() {
        selected = new ArrayList<IResource>();

        ISelection selection = provider.getSelection();
        if (!selection.isEmpty()) {
            IStructuredSelection sSelection = (IStructuredSelection) selection;
            if (sSelection.size() >= 1) {
                Iterator iterator = sSelection.iterator();
                while (iterator.hasNext()) {
                    Object element = iterator.next();
                    if (element instanceof IAdaptable) {
                        IAdaptable adaptable = (IAdaptable) element;
                        IResource resource = (IResource) adaptable.getAdapter(IResource.class);
                        if (resource != null) {
                            selected.add(resource);
                            continue;
                        }
                    }
                    // one of the elements did not satisfy the condition
                    selected = null;
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    protected List getSelectedResources() {
        return selected;
    }
    
    /*
     * (non-Javadoc) Method declared on IAction.
     */
    public void run() {
        if(!isEnabled()){ //will also update the list of resources (main change from the DeleteResourceAction)
            return;
        }
        super.run();
    }


}

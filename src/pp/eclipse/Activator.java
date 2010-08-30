package pp.eclipse;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import pp.eclipse.cache.Cache;
import pp.eclipse.cache.CacheStrategy;
import pp.eclipse.common.DefinedItem;
import pp.eclipse.common.DefiningFile;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "pp-eclipse"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    /**
     * The constructor
     */
    public Activator()
    {
//        Logger logger = Logger.getLogger(PLUGIN_ID);
       
        // And import
        
//        The copies in the build directory should be marked as derived (IResource.setDerived()). 
//        Derived resources are then filtered out of the open resource dialog. Whoever is copying those resources should be marking the 
//        copies as derived - the Java builder does this, for example.
    }
    
    private Map<Class<?>, CacheStrategy<?, ?>> strategies = new HashMap<Class<?>, CacheStrategy<?,?>>();
    public <I extends DefinedItem, C extends DefiningFile<I>> CacheStrategy<I, C> cacheStrategy(Class<I> forItem) 
    {
        synchronized (strategies) {
            @SuppressWarnings("unchecked")
            CacheStrategy<I, C> cacheStrategy = (CacheStrategy<I, C>) strategies.get(forItem);
            if (cacheStrategy == null) {
                cacheStrategy = Cache.post();
                cacheStrategy.startup();
                strategies.put(forItem, cacheStrategy);
            }
            return cacheStrategy;
        }
    }

    public IWorkspaceRoot getWorkspaceRoot() {
        return ResourcesPlugin.getWorkspace().getRoot();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext context) throws Exception
    {
        super.start(context);
        //ResourcesPlugin.getWorkspace().addResourceChangeListener(repository.getResourceListener());
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    public void stop(BundleContext context) throws Exception
    {
        //ResourcesPlugin.getWorkspace().removeResourceChangeListener(repository.getResourceListener());
        plugin = null;
        synchronized (strategies) {
            for (CacheStrategy<?, ?> strategy : strategies.values()) {
                strategy.shutdown();
            }
            strategies.clear();
            strategies = null;
        }
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault()
    {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * 
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path)
    {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
}

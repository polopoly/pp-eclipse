package pp.eclipse.domain;

import java.util.List;

import org.eclipse.core.runtime.IPath;

import pp.eclipse.common.DefiningFile;


public class TemplateDefinition implements DefiningFile<InputTemplate> {
    public final IPath path;
    public final long modified;
    public final List<InputTemplate> templates;
    
    public TemplateDefinition(IPath path, long modified, List<InputTemplate> templates) {
        this.path = path;
        this.modified = modified;
        this.templates = templates;
    }

    @Override
    public String toString() {
        return "TemplateDefinition [path=" + path + ", modified=" + modified
                + ", templates=" + templates + "]";
    }

	@Override
	public IPath path() 
	{
		return path;
	}
	
	@Override
	public long modified() 
	{
		return modified;
	}

	@Override
	public List<InputTemplate> defines() 
	{
		return templates;
	}
}

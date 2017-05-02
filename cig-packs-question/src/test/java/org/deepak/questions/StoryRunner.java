package org.deepak.questions;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromURL;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;

/**
 * Should run all the stories, but doesn'
 * @author abrahd2
 *
 */
public class StoryRunner extends JUnitStories {

		 
	    public StoryRunner() {
	        // configure as TraderStory except for
	        Configuration configuration = new MostUsefulConfiguration()
	                .useStoryLoader(new LoadFromURL());
	    }
	 
	    @Override
	    protected List<String> storyPaths() {
	        String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
	        return new StoryFinder().findPaths(codeLocation, asList("**/*.story"),
	                    asList(""));// this addtional parameter: "file:"+codeLocation, was causing a storyNotFoundException, since it was trying to load from claspath using absolute file,path
	    }
}

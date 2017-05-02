package org.deepak.questions.stories;

import org.deepak.questions.steps.MinNumberOfCigPacksSteps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

/**
 * Since <code>LoadFromClasspath</code> is used to load the stories, the corresponding story will only be found if it is with the same name of the class in lowercase with '_' 
 * separating each word AND should be in the same classpath package
 * @author abrahd2
 *
 */
public class MinNumberOfCigPacks extends JUnitStory {

	// Here we specify the configuration, starting from default MostUsefulConfiguration, and changing only what is needed
    @Override
    public Configuration configuration() {
				return new MostUsefulConfiguration()
				    // where to find the stories
            .useStoryLoader(new LoadFromClasspath(this.getClass().getClassLoader())) 
//				.useStoryLoader(new LoadFromRelativeFile(new URL("**/*.story")))
				
				    // CONSOLE and TXT reporting
				    .useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(Format.CONSOLE, Format.TXT));
    }
 
    // Here we specify the steps classes
    @Override
    public InjectableStepsFactory stepsFactory() {       
        // varargs, can have more that one steps classes
        return new InstanceStepsFactory(configuration(), new MinNumberOfCigPacksSteps());
    }
}

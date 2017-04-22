package net.mostlyoriginal.game;

import com.artemis.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Example tests for your game / systems.
 *
 * @author Daan van Yperen
 */
public class ExampleTest {

    @Test
    public void When_running_tests_Should_have_access_to_fluid_interface()
    {
        class ExampleSystem extends BaseSystem {
            @Override
            protected void processSystem() {
                Assert.assertNotNull(E.E());
            }
        }

        processTestworld(new ExampleSystem());
    }

    private void processTestworld(BaseSystem o) {
        new World(new WorldConfigurationBuilder()
                .with(new SuperMapper(), o).build()).process();
    }
}

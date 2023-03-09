package ru.malletmustdie.cibinternstesttask.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionConfigurationException;
import org.junit.jupiter.api.extension.ExtensionContext;
import ru.malletmustdie.cibinternstesttask.DatabaseContainer;

/**
 * The type PostgresContainerExtension container.
 */
public class PostgresContainerExtension implements BeforeEachCallback, BeforeAllCallback {

    private static final DatabaseContainer DATABASE_CONTAINER = DatabaseContainer.getInstance();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        if (DATABASE_CONTAINER == null) {
            throw new ExtensionConfigurationException(
                    String.format("Container not founded for %s", extensionContext.getTestClass()));
        }
        if (!DATABASE_CONTAINER.isRunning()) {
            throw new ExtensionConfigurationException(
                    String.format("Container not running for %s", extensionContext.getTestClass()));
        }
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        if (!DATABASE_CONTAINER.isRunning()) {
            DATABASE_CONTAINER.start();
        }
    }

}

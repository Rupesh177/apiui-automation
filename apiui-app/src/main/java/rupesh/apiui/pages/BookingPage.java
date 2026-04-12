package rupesh.apiui.pages;

import rupesh.apiui.core.driver.Driver;
import rupesh.apiui.core.driver.DriverManager;
import rupesh.apiui.core.featureFlag.FeatureFlagService;


public class BookingPage {

    private final Driver driver = DriverManager.getDriver();
    private final FeatureFlagService featureFlagService = new FeatureFlagService();

    // -------------------------------
    // LEGACY FLOW LOCATORS
    // -------------------------------
    private final String legacyFlowContainer = "//div[@id='legacy-booking-flow']";
    private final String legacyContinueBtn = "//button[@id='legacy-booking-continue']";

    // -------------------------------
    // NEW FLOW LOCATORS
    // -------------------------------
    private final String newFlowContainer = "//div[@id='new-booking-flow']";
    private final String newContinueBtn = "//button[@id='new-booking-continue']";

    // -------------------------------
    // COMMON LOCATORS
    // -------------------------------
    private final String sourceCityInput = "//input[@id='source-city']";
    private final String destinationCityInput = "//input[@id='destination-city']";
    private final String searchBtn = "//button[@id='search-booking']";
    private final String bookingConfirmation = "//div[@id='booking-confirmation']";

    public void enterSource(String source) {
        driver.type(sourceCityInput, source);
    }

    public void enterDestination(String destination) {
        driver.type(destinationCityInput, destination);
    }

    public void search() {
        driver.click(searchBtn);
    }

    public void proceedBooking() {
        boolean newFlow = featureFlagService.isEnabled("NEW_BOOKING_FLOW");

        if (newFlow) {
            driver.click(newContinueBtn);
        } else {
            driver.click(legacyContinueBtn);
        }
    }

    public boolean isNewFlowVisible() {
        try {
            String text = driver.getText(newFlowContainer);
            return text != null && !text.isBlank();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLegacyFlowVisible() {
        try {
            String text = driver.getText(legacyFlowContainer);
            return text != null && !text.isBlank();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBookingConfirmed() {
        try {
            String text = driver.getText(bookingConfirmation);
            return text != null && !text.isBlank();
        } catch (Exception e) {
            return false;
        }
    }

    public void bookFlight(String source, String destination) {
        enterSource(source);
        enterDestination(destination);
        search();
        proceedBooking();
    }
}
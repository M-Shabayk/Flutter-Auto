// Imports the Flutter Driver API.
import 'package:flutter_driver/flutter_driver.dart';
import 'package:test/test.dart';

void main() {
  group('Counter App', () {
    // First, define the Finders and use them to locate widgets from the
    // test suite. Note: the Strings provided to the `byValueKey` method must
    // be the same as the Strings we used for the Keys in step 1.
    final counterTextFinder = find.byValueKey('counter');
    final buttonFinder = find.byValueKey('increment');

    late FlutterDriver driver;

    // Connect to the Flutter driver before running any tests.
    setUpAll(() async {
      driver = await FlutterDriver.connect(
        printCommunication: true,
      );
    });

    // Close the connection to the driver after the tests have completed.
    tearDownAll(() async {
      if (driver != null) {
        await driver.close();
      }
    });

    test('starts at 0', () async {
      final h = await driver.checkHealth();
      expect(h.status, HealthStatus.ok);
      await driver.clearTimeline();
      final renderObject = await driver.getRenderObjectDiagnostics(
          counterTextFinder,
          subtreeDepth: 2,
          includeProperties: true);

      // Use the `driver.getText` method to verify the counter starts at 0.
      expect(await driver.getText(counterTextFinder), "0");
      await driver.getBottomLeft(counterTextFinder);
      await driver.getTopRight(counterTextFinder);
    });

    test('increments the counter', () async {
      // First, tap the button.
      await driver.tap(buttonFinder);
      await driver.tap(buttonFinder);

      // Then, verify the counter text is incremented by 1.
      expect(await driver.getText(counterTextFinder), "2");
    });

    test('navigates to second route', () async {
      await driver.tap(find.byTooltip('Increment'));
      expect(
          await driver.getText(find.descendant(
              of: find.byTooltip('counter_tooltip'),
              matching: find.byValueKey('counter'))),
          '3');

      await driver.tap(find.byType('TextButton'));
      await driver.waitForAbsent(find.byTooltip('counter_tooltip'));
      expect(await driver.getText(find.text('This is 2nd route')),
          'This is 2nd route');
    });
  });
}

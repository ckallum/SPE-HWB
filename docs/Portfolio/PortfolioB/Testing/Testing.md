Things to Include:

- Firebase Test Lab
    - Didn't include in CI as it was too costly if run on every push
    - Test Lab to test your app on physical devices before you release app updates with significant changes in UI and functionality. This will help to ensure that your app runs well on a wide range of popular physical devices, and also ensures test coverage for any app functionality that relies on physical device features that are not simulated by virtual devices
    - Robo Test
        - Tests activities and navigation
        - Video
        - Sitemap

    - Instrumentation Test
        - Test all UI elements
        - Unit test for physical device -> slower execution -> evaluates apps behaviour against 
          device hardware. 
        - These are used to control Android API -> components such as button clicks and application 
          lifecycle -> slow 
        - Using Espresso Scripts
          
- Local Tests/Unit tests
    - Isolates components under test i.e. class specific behaviour / methods -> doesn't need to 
      connect to android device aka faster. 
    - Test database connections etc.
    - Mock tests via Mockito -> mock dependencies of classes i.e. Firebase databases 
    - Junit
    
- Codacy
- Circle Ci
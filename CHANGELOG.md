# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project
adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.2.0] - 2023-07-12

### Added

- Added the Firebase configuration files for the development and production environments, and
  Firebase analytics and crashlytics integration.
- Added dimens for different screen size densities, new theme values and major colors for the app.
- Added more app structure and boilerplate code for the login fragment.
- Populated home fragment and SME registration fragments of the main activity with their respective
  views.
- Added SME terms and conditions activity and respective fragments.
- Added SME KYC details activity and respective fragments.
- Created the shared preferences manager class and added logic for tracking when the app is launched
  for the very first time in order to display the on-boarding screen to the user.

### Changed

- Renamed the application class to `PezeshaAgentApp`.
- Changed the launch activity to be the `LoginActivity` and implemented the modern splash screen
  with backwards compatibility as provided by the new SplashScreen API from Andri0d 12.
- Renamed the labels on the bottom navigation bar of the main activity.
- Disabled a number of custom themes and widget styling for further customizations with respect to
  different screen sizes.

###

- Removed the legacy splash screen implementation and deleted the launch screen activity.

## [0.1.1] - 2023-04-03

### Added

- Application launcher icon.
- Activity for on-boarding new users.
- Login activity, main activity and fragments for the main activity.

## [0.1.0] - 2023-04-01

### Added

- Initialized this project

[Unreleased]: https://bitbucket.org/pezeshaworld/pezesha-agent-android/branches/compare/develop..v0.2.0

[0.2.0]: https://bitbucket.org/pezeshaworld/pezesha-agent-android/branches/compare/v0.2.0..v0.1.1

[0.1.1]: https://bitbucket.org/pezeshaworld/pezesha-agent-android/branches/compare/v0.1.1..v0.1.0

[0.1.0]: https://bitbucket.org/pezeshaworld/pezesha-agent-android/commits/tag/v0.1.0

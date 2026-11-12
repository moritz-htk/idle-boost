<h2>Changes</h2>

- Added Forge support on 1.21.1
- Added an adjustable delay before background optimizations activate
  - Defaults to 2 seconds and can be set from 0 to 60 seconds
  - Set to 0 for immediate activation
- Background limits no longer increase an already lower frame rate or render distance
- Original settings are now restored when Idle Boost or an individual optimization is disabled
- Original settings are now restored when leaving a world
- Fixed configuration toggles not always matching their selected values
- Added Arabic, Dutch, French, Hindi, Italian, Japanese, Korean, Polish, Russian, Spanish, Swedish, and Turkish translations
- Improved existing translations
- Changed license to GPL 3.0

<h2>Technical Changes</h2>

- Added `backgroundDelaySeconds` to the configuration file
- Configuration values are now validated when loading and saving
- Invalid configuration files are backed up to `idle_boost.json.broken` before defaults are created
- Configuration read errors now fall back to defaults and block saving until a successful reload
- Configuration files are now saved as UTF-8 through a temporary file
  - Atomic replacement is used when supported by the file system
- Background settings are now checked each client tick instead of only on focus changes
- Activation delay uses elapsed time rather than tick counts
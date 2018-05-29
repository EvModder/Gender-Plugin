# GenderLib
Minecraft plugin allowing players to specify their gender

## Purpose
This plugin is primarily intended to be used as a tool/library for other plugins to help personalize messages\
Supports non-binary genders if server administrators so choose.\

### Usage
Using API calls provided by this plugin, we can reformat\
`"PurplePheonix has reached their destination. They are now at 20 points"`\
to\
`"PurplePheonix has reached her destination. She is now at 20 points"`

#### Ternary operator
Here's a handy code shortcut you can use:\
`print("EvDoc is amazing. "+(gender == male ? "He" : "She")+" is a developer of this plugin!");`\
or more formally:\
`result = <boolean expression> ? <value if true> : <value if false>`

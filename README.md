# GenderLib
Minecraft plugin allowing players to specify their gender

## Purpose
Supports non-binary genders if server administrators so choose.\
This plugin is primarily intended to be used as a tool/library for other plugins to help personalize messages
\
### Usage
Using API calls provided by this plugin, we can reformat\
`"PurplePheonix has reached their destination. They are now at 20 points"\`
to\
`"PurplePheonix has reached her destination. She is now at 20 points"`

#### Ternary operator
Here's a handy code shortcut you can use:\
"\<a message\>" + (gender == male ? "He" : "She") + "\<rest of message\>"

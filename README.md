## Requirements
### functional
- Grab content from the URL and Find the 10th character and display it on the screen ✅
- Grab content from the URL and Find every 10th character (i.e. 10th, 20th, 30th, etc.) and display the array on the screen ✅
- Grab content from the URL and Split the text into words using whitespace characters (i.e. space, tab, line break, etc.), count the occurrence of every unique word (case insensitive) and display the count for each word on the screen ✅

### non-functional
- clean, understandable and robust code, optimal data structures and low computational complexity.
- reusability, extensibility, correctness, state and error handling.
- utilize the right amount of design patterns and best practices in your code.
- solution should be well structured, but also not over-engineered.

## has
_I like to modularize application for few reasons (separation, parallel builds, plugins, dynamic features, instant app, possibility to go multiplatform)_
- common module - in ideal world can be shared with iOS through kotlin multiplatform
   - holds entities, logic
- remote module - should be easily replacable
    - holds api calls
    - usually I try to define contract in common module so I can replace it
- app - or feature module
    - holds activities, fragments UI, ViewModels
    - usually I have extra module for features and one core module for sharing android spacific code

## uses
- coroutines - great for async work, simple api,
- ViewModel, LiveData -  I prefer working with it. It's super simple and it covers most of android problems
- retrofit - saves some time, wasn't sure if I even need this one for such a simple app

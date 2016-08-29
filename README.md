## Overview

### Privacy Friendly App Shopping List

The Privacy Friendly App Shopping List is an Android application that does not require any permissions from the user in order to be installed. With this app users will be able to manage their shopping lists. Basically users will be able to add, edit and remove lists. A shopping list contains products which can be added, edited and removed as well.

## Motivation

Nowadays there are many apps that require many or all permissions available in Android in order to be installed. However these apps do not always need all of the permissions they ask for. With this project we want to offer an app where the user can be sure that private information such as contacts, location, identity etc., are not being used by the application.

## Versioning
Version 0.4 (Beta). Changes:
- Checking products onClick had been added to allow the user checking products when the floating action button ("+") was covering the checkbox by clicking on the CardView. This solution was not very intuitive because items would move to the bottom if the user accidentally clicked a product. Therefore the checkbox was added again in the ProductEditDialog. Users can check the product in this dialog if the checkbox is being covered by the floating action button ("+").
- Bug fix: product names can be repeated in different lists. The validation that avoid adding a product twice happens only inside a list.
- Bug fix: when a list is deleted all its product are deleted from the database as well
- Vertical bar of CardViews in the list overview turns grey if there are no unchecked products in the shopping list (all products checked, or list is empty)
- Responsiveness improvement: All database access operations are perform asynchronously by using the RxJava API and the RxAndroid extension

Version 0.3 (Beta). Changes:
- When edit/create dialogs (for list or products) are opened, then the keyboard is automatically showed, so the user can start typing.
- After creating a new list, the app jumps automatically inside of the new list, so the user can immediately start adding products.
- Tutorial Dialog layouts bugs fixed
- Bug fixes in Camara feature
- Share as Text feature added
- Length of List name and Product name increased to 40. If the text is too long, then "..." is showed at the end of the line
- Dynamic counter added in text fields List Name and Product Name to show the users how many characters they have left
- Product names cannot appear multiple times in the same list. Error message added so the user realizes that the product already exists
- onClick event inside a shopping list set the checkbox value to true. This feature was added because sometimes the CardView was behind the floating action button and therefore the user could not click the checkbox.
- If lists are empty, then the logo of the app and a message are shown to user. This feature was added to to fill the empty white space.

Version 0.2 (Beta). Changes:
- Statistics (total units) were added to the chart
- Statistics can be enabled or disabled for each list
- Sorting options are saved for each product list
- A reminder can be set for each list. If the notifications are enabled, then a notification pops up when the date of the reminder has been reached.
- Toasts were added to improve the user experience
- The user can use the camera to take a picture when editing or adding products. This picture can only be accessed through the Shopping List app.
- Zoom available for product images.
- Welcome Dialog added
- Tutorials added
- Tutorials can be enabled/disabled in the settings section
- List and product items are expandabled. When expanded, the details of the item can be seen.
- Lists and products can be deleted one by one on long press. Multiple deletion of elements is still available.
- Help section was updated

Version 0.1 (Beta). Features:
- Lists can be added, edited and removed
- Lists can be sorted by name or priority
- Products can be added, edited and removed from lists
- Products can be sorted by name, store, quantity, price or category
- Statistics (total amount) can be visualized in a chart
- Statistics' chart can be filtered by range and the results can be showed grouped by month, week, day, category, store or product.
- Statistics can be turned on/off inside a list
- Statistics option can per default be set to be turned on/off in the settings
- Statistics can be fully deleted from the settings menu
- Currency can be set by the user in the setings menu (up to 3 characters)

## Installation

The app can be installed by using IntelliJ or Android Studio.

1. Download the source code: $ git clone https://github.com/SecUSo/privacy-friendly-shopping-list.git
2. Add the local.properties and the build.properties file to the root by removing the extension ".copy" from "local.properties.copy" and "build.gradle.copy"
3. Open the IDE of your choice (recomened: IntelliJ or Android Studio)
4. Connect the mobile device to the computer
5. Setup a Run Configuration "Android Application" and select "app" under Module
6. Click run and select "Choose a running device" from the "Device Chooser" Dialog


## License

Copyright 2016 by Grebiel J. Ifill B. and Christian König

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Contributors
Prof. Dr. Melanie Volkamer,
Christopher Beckmann,
Karola Marky,
Peter Mayer

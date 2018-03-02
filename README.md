![CapTech](https://techchallenge.captechlab.com/tech-challenge-general/tech-challenge-creation/raw/d8435683d964afadb54685f35d7d69ad0cbeac70/images/Logo.png)

# Assignment 4: Fragments Dialogs and Navigation
This assignment, the fourth in the series, will familiarize the student with several important concepts required to support the myriad of Android devices and their unique specifications.

## Table of Contents

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [Overview](#overview)
- [Learning Objectives](#learning-objectives)
- [Prerequisites](#prerequisites)
- [Grading Criteria](#grading-criteria)
- [Helpful Resources](#helpful-resources)
- [Submission Instructions](#submission-instructions)
  - [Git Workflow](#git-workflow)
- [Help and Discussion](#help-and-discussion)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Overview
The student is to build a Recipe Application to help manage food recipes.  There are 5 distinct pieces of functionality that should be delivered with the application:

1. Category List (i.e., Appetizer, Dessert, Main Dish, Side Item)
2. Recipe List
3. Recipe Details
    - Name
    - Category
    - Total Time (minutes)
    - Ingredients (free form)
    - Instructions (free form)
4. Create New Recipe
5. Delete Recipe

Upon first launch, the user should be presented a list of recipe categories.  Upon selecting a category, the user should be presented with a list of recipes within the selected category.  The list of recipes should show the name and the amount of time required to prepare the recipe.  Once the user selects a recipe, the selected recipe’s details should be displayed.  The user should be able to delete a recipe from either the list screen or the details screen, which should be persisted in the database.  A confirmation dialog should be used if/when it is appropriate (hint: platform version, method for selecting, activity, and your specific implementation will depend on whether you should require confirmation).

The ability to create a new recipe should be made available at all times and should be presented to the user in a fashion familiar to the version of Android the device is running on.  After creation of a new recipe, the application should navigate to the recipe details and display the newly created recipe.  From the newly created recipe’s detail screen, hitting the back Button should not go back to the create new recipe functionality, instead it should go back to the last screen before the create new recipe functionality.  If an Action Bar is present and the user selects up from the recipe detail screen, the appropriate recipe list for that category should be displayed.

A pre-populated SQLite database will be provided so the student will have existing data and a known data model to work with. Please see the file: "*./supporting-resources/assignment4.db*".

## Learning Objectives
When completed the student should have an understanding of the following concepts:

* Multi-OS Support
* Action Bar/Menu
* Fragmentation support
* Navigation & Task Stack

## Prerequisites
Before starting this tech challenge, a student should be familiar with the follow concepts and have the following environment

* All prior Android Assignments are completed
* The Android SDK installed on the development device
* Android Studio (the latest stable release) installed on the development device

## Grading Criteria
Here are some specific requirements the graders will be looking for in your submission in order for your submission to be accepted:

The application must adhere to the following requirements:

* The application should be supported on Gingerbread, Ice Cream Sandwich and Jelly Bean devices.
* The application should be designed to follow the conventions and UI paradigms for that platform.
    * ICS+ devices should leverage the Action Bar
    * Gingerbread devices shouldn’t have an Action Bar and should leverage the regular Menu
* The application should support large screen devices (tablets) by following the appropriate UI conventions
    * You may limit large screen support to only ICS+
* If an Action Bar is present, it should follow typical Action Bar conventions for navigation (Up vs Back)
* Fragments must be used
* All dialogs should be Fragments

## Helpful Resources
These are some helpful documentation links and resources to help you be successful in completing this tech challenge:

* For the needed database file, see: "./supporting-resources/assignment4."
* Web resources
    * [http://developer.android.com/training/basics/supporting-devices/index.html](http://developer.android.com/training/basics/supporting-devices/index.html)
    * [http://developer.android.com/training/backward-compatible-ui/index.html](http://developer.android.com/training/backward-compatible-ui/index.html)
    * [http://developer.android.com/guide/topics/ui/actionbar.html](http://developer.android.com/guide/topics/ui/actionbar.html)
    * [http://developer.android.com/guide/topics/ui/menus.html](http://developer.android.com/guide/topics/ui/menus.html)
    * [http://developer.android.com/training/basics/fragments/index.html](http://developer.android.com/training/basics/fragments/index.html)
    * [http://developer.android.com/training/design-navigation/index.html](http://developer.android.com/training/design-navigation/index.html)
    * [http://developer.android.com/training/implementing-navigation/index.html](http://developer.android.com/training/implementing-navigation/index.html)

## Submission Instructions
Please provide these specific items when submitting your tech challenge, placing them inside of your repository where your grader can easily find them:

* An application that meets the above requirements running on the target mobile device is required for completion of this assignment. Please place the `.apk` file of the completed app in the base directory of your repository.
* The code for your submission should be at the base directory of the repo, or within a specified child directory.
Your application will be run on several actual devices of differing form factors, running different versions of Android.  Please attempt to use the emulator to test your solution in as many configurations as possible to ensure it works.

### Git Workflow
* When you start a tech challenge, the tech challenge admin will create a private repository for you.
* There will be two branches in the repo, `master` and `develop`.
* Do all your work in the `develop` branch.
* As you work, push your changes up to your GitLab repo.
* When you are finished, make a merge request to the `master` version of your private repo.
* At this point, the graders will review your merge request and offer comments where needed.
* You may be asked to push updates, corrections to your develop branch in response to the coments by graders. You `don't` need to create another merge request.
* When the grader is satisfied, he will merge your code into the master branch of your private repo.
* Now celebrate, the Tech Challenge is completed!

## Help and Discussion

If you need help on this Tech Challenge or would like to discuss it / leave feedback, please check out the [Mobile Tech Challenges team](https://teams.microsoft.com/l/team/19:3c6cb4196d9d47b9ad2510126e14ad44@thread.skype/) in Microsoft Teams, especially the [Help and Discussion channel](https://teams.microsoft.com/l/channel/19:bb28957ec53d452787c8c3aeae850127@thread.skype/Mobile%20-%20Android%20-%20Help%20and%20Discussion).





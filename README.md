# Infinum Academy: Shows App
This repository contains my work done for Infinum Academy Android course. During 4 weeks of the course we had 8 lectures and 8 assignments through which we created the Shows app.

## Design
For the looks of our app we were given Figma designs for each of the assignments (where app design was being updated):
- [Figma design](https://www.figma.com/file/4jnWB7pHRandFH16rP5mXc/Assignment-2) for the look of login page
- [Figma design](https://www.figma.com/file/kQ8Ch8VL4DNp6vKX0TctiP/Assignment-3) for the look of shows and show details fragments
- [Figma design](https://www.figma.com/file/3THkJjnG5MfADEE3gxwzSS/Assignment-5) for the look of user bottom sheet
- [Figma design](https://www.figma.com/file/XdAtAPVG8nur6YPiAqhf3y/Assignment-6) for the look of registration page
- [Figma design](https://www.figma.com/file/FCEuutdrImNpX2VDytv9nE/Assignment-8) for the look of Splash screen

## Description
In assignment6 our app was connected to the [Tv Shows API](https://tv-shows.infinum.academy/api/v1/docs/).
When user clicks the app icon ih his phone he is welcomed with the splash screen. Upon opening the app user is located on the login page. There he can see two animations. One for the shows icon and one for the shows text. From there he can either login or register if he doesn't have an account yet. This is done through the API. User has the option of checking the _Remember me_ checkbox so next time he opens the app he will already be logged in and on the next screen.
Once user logs in he has the option of selecting the _Load shows_ button, which fetches the shows and its details from the API, or clicking top right image which displays the bottom sheet where user can choose to change his profile picture or logout.
A spinner is displayed when the user selects to load the shows. Once the shows are loaded the user can select a show and see its details, reviews and write a review of his own.

If the user's device isn't connected to the internet, the shows and show details will be fetched from the database and displayed.

## Technologies
For the networking part we used [Retrofit](https://square.github.io/retrofit/), [OkHttp](https://square.github.io/okhttp/) and [Chucker](https://github.com/ChuckerTeam/chucker) to track requests and responses.

For the database we used [Room](https://developer.android.com/jetpack/androidx/releases/room?gclid=CjwKCAjw3K2XBhAzEiwAmmgrAhhZWpGb7yYOo2S6cNVgh4iEs6nR1DtQFssC4BTYio_tRXRrApjOcxoCgSIQAvD_BwE&gclsrc=aw.ds).

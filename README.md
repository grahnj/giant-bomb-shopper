# Gravie Software Engineer Challenge

## Developer

Justin Grahn 2021.04.25

## Instructions

To run the Giant Bomb Shopper SPA, you'll need to perform some initial steps to ensure that everything is working.

1. In the source folder of Giant Bomb Shopper, copy the __props-template.cljs__ file into a new file called __props.cljs__. Where you see `"{YOUR API KEY}"`, replace the text with the API key for your user/application. This file is in the .gitignore file on purpose -- your API key should never be added to your repo!

2. From a command prompt, navigate to the `/giant-bomb-proxy` folder and run `npm run start`. This will start a proxy service on port `:9001` to enable your outbound calls to the Giant Bomb API.

3. From a second command prompt, navigate to the `/giant-bomb-shopper` folder and run `npx shadow-cljs watch app`. This will run your app server on the `:8280` port in `watch` mode, which allows you to make hot-swappable changes to your UI.

If you've done everything correctly, you should now be able to use the app at [http://localhost:8280/#](http://localhost:8280/#)

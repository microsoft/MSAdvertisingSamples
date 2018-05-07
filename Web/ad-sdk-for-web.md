---
author: mcleanbyron
description: Get started with the Microsoft Advertising SDK in your websites.
title: Get started with the Microsoft Advertising SDK in your websites
---

# Get started with the Microsoft Advertising SDK in your websites (beta)

Increase your revenue opportunities by using the Microsoft Advertising SDK to show ads in your websites. Our SDK offers video and native ads and supports mediation with many popular ad networks.

## Import the Microsoft Advertising SDK into your website

The Microsoft Ads SDK is available at the following location.

```html
<script src="https://msmonplat.microsoft.com/core/1/latest.min.js"></script>
```

Though you can download the JavaScript file and serve it from your own location, we recommend that you load the file dynamically. This will ensure you receive the latest SDK.  

## SDK versioning

The Microsoft Advertising SDK has a major and a minor version. For example, if the current major version is 1 and the current minor version is 5, the latest JavaScript file can be found at ```https://msmonplat.microsoft.com/core/1/5.min.js```.

You can reference a specific version number if you don't want to get the latest updates. However, we recommend that you point to the latest version. This will make sure that you get the latest bug fixes done for the major version. For example, pointing to ```https://msmonplat.microsoft.com/core/1/latest.min.js``` will always get you the latest SDK for the major version 1. 

The major version is incremented only when there are major updates to the SDK which require retesting your website.

## Add a video ad

Video ads display at a specific location in your website. You can choose the exact place where you want to show a video ad.

### Update your HTML file

In your HTML file, add the reference to the Microsoft Advertising SDK JavaScript file and the container where the video ad will play.  

```html
<html lang="en">
<head>
<script src="https://msmonplat.microsoft.com/core/1/latest.min.js">
</script>
</head>
<body>
    <div style='width: 640px; height: 360px;'>
        <div id='slot'>
            <video id='videoPlayer' autoplay style="background-color:black; width: 640px; height: 360px;"></video>
        </div>
    </div>
</body>
</html>
```

### Update your JavaScript file

In JavaScript code that runs after the Microsoft Advertising SDK JavaScript file has loaded, add code similar to the following example.  

```javascript
microsoftAdsSdk
        .ads
        .fetch {
            adUnitId: ‘test’,
            applicationId: ‘applicationId’,
            format: "video",
            overrides: {  
                  'imp[0].video.w': 640,  
                  'imp[0].video.h': 360  
                },
            settings: {
                videoSlot: 'videoPlayer',  
                slot: 'slot',  
                onAdError: onVideoAdError,  
                onAdEnd: onVideoAdEnd,  
                onAdReady: onVideoAdReady,  
                isSite: true,
                timeouts: {  
                    adReady: 30, /* 30 seconds ad ready timeout */
                    adStart: 5  /* 5 seconds ad play timeout */
                }
            }
        }
 );
```

Note the following details about some of the fields:

* **adunitId**: Replace this string from the code example with the ID for the [ad unit](#ad-units). To display a test ad, pass the value *test*.
* **applicationId**: The application ID assigned to your website by Microsoft.
* **videoSlot**: The ID attribute of the video tag in your HTML.
* **slot**: The ID attribute of the div holding the video tag in your HTML.
* **timeouts.adReady**: The time (in seconds) after which the request to fetch a video ad should time out.
* **timeouts.adStart**:  The time (in seconds) after which the request to start a video ad times out. At times, a video ad might require some time to start playing after the ad ready is fired. You can use this parameter to limit the timeout in such scenarios.   

Next, define the callback functions and invoke the **showAd** method.

```javascript
/**
 * Callback called when the ad is ready.
 */
function onVideoAdReady(adObject) {
    console.log('AdObject: ' + JSON.stringify(adObject));

    // Show the ad.  
    microsoftAdsSdk
            .ads
            .showAd(adObject);
     }

/**
 * Callback called when the ad has ended.
 */
function onVideoAdEnd(adObject) {
    // Video ad has ended
}

/**
 * Callback called when there was an error fetching/rendering the ad.  
 */
function onVideoAdError(adObject, errorResponse) {
    console.error('AdObject: ' + JSON.stringify(adObject) + '; error: ' + JSON.stringify(errorResponse));

}
```

## Add a native ad

Native ads are ads where you have some control over the look and feel of the ad. Our service returns all the assets of the ad individually, including the ad title, and you can decide how to place them in your website. You can blend these assets with your other website components and provide a consistent user experience.

### Update your HTML file

In your HTML file, add the reference to the Microsoft Advertising SDK JavaScript file and the container where the native ad will be displayed.  

```html
<html lang="en">
<head>
<script src="https://msmonplat.microsoft.com/core/1/latest.min.js">
</script>
</head>
<body>
    <div id='nativeSlot' style="border: 1px solid grey; position: relative;">
        <img id="adImage" style="width: 300px;"></img>
        <div id="adTitle" style="font-weight: 700;"></div>
        <div id="adDescription"></div>
        <span style="position: absolute; top: 0px; right: 0px; background-color: black; color: white; font-family: Segoe UI Light; font-size: 0.8em;padding: 3px 10px;">AD</span>
    </div>
</body>
</html>
```

### Update your JavaScript file

In JavaScript code that runs after the Microsoft Advertising SDK JavaScript file has loaded, add code similar to the following example.  

```javascript
microsoftAdsSdk
         .ads
         .fetch {
             adUnitId: ‘test’,
             applicationId: ‘applicationId’,
             format: "native",
             settings: {
                 slot: 'nativeSlot',  
                 onAdError: onNativeAdError,  
                 onAdReady: onNativeAdReady,  
                 isSite: true
             }
         }
  );
```

Note the following details about some of the fields:

* **adunitId**: Replace this string from the code example with the ID for the [ad unit](#ad-units). To display a test ad, pass the value *test*.
* **applicationId**: The application ID assigned to your website by Microsoft.
* **slot**: The ID of the container in your HTML that hosts the native ad.   

Next, define the callback functions and invoke the **showAd** method. The SDK handles the click event for the native ad container, passed via the **slot** property.

```javascript
/**
 * Callback called when the ad is ready.
 */
function onNativeAdReady(adObject, nativeAdObject) {
    console.log('NativeAdObject: ' + JSON.stringify(nativeAdObject));

    // Show the ad.  
    microsoftAdsSdk
        .ads
        .showAd(adObject);
     }

/**
 * Callback called when there was an error fetching/rendering the ad.  
 */
function onNativeAdError(adObject, errorResponse) {
    console.error('AdObject: ' + JSON.stringify(adObject) + '; error: ' + JSON.stringify(errorResponse));
}
```

The **nativeAd** object contains various assets that you can integrate inside your **slot** container using your own choice of fonts, colors and animations.  

> [!NOTE]
> At a minimum, you must show the description and title in your native creative.

|  Property  |  Description  |
|------------|---------------|
| **callToAction** | Gets the call to action text for the native ad. |
| **description** | Gets the description for the native ad. You must display the description in your native creative.  |
| **iconImage** | Gets the icon image for the native ad, as supplied by the ad sponsor. Usually these are small images to be used in the ad to indicate the ad source.  |
| **mainImages** | Gets the main images for the native ad, as supplied by the ad sponsor.  |
| **sponsoredBy** | Gets the brand name of the sponsor for the product that is being advertised in the native ad.  |
| **title** | Gets the title for the native ad. You must display the title in your native creative. |

The **iconImage** and **mainImages** properties contain image objects with the following properties.

|  Property  |  Description  |
|------------|---------------|
| **height** | Gets the height of the icon or main image for the native ad.  |
| **source** | Gets the source URI of the icon or main image file for the native ad.   |
| **width** | Gets the width of the icon or main image for the native ad.   |

The following example demonstrates a JSON content string for a native ad.

```
{
  "title":"Visual Studio 2017",
  "description":"Productivity for any device, app and platform.",
  "callToAction":"DOWNLOAD VISUAL STUDIO 2017",
  "sponsoredBy":"Sponsored by",
  "adIcon":null,
  "iconImage":
  {
    "source":"https://adbrokercdn.blob.core.windows.net/images/logo-white.png",
    "width":184,
    "height":42
  },
  "mainImages":[
  {
    "source":"https://adbrokercdn.blob.core.windows.net/images/hero-image-large.png",
    "width":491,
    "height":276
  }]
}
```

## Ad units

When you add code to display ads in your website, you must pass an ad unit ID to the *adUnitId* parameter of the **AdControl** constructor. To test your code, pass the string *test* to the **adUnitId** parameter of the **AdControl** constructor. This retrieves a test ad from our service. Test ads can only be used for internal testing.

For the public version of your website, you must update your code to pass a live ad unit ID to the *adUnitId* parameter. To get a live ad unit ID to use with the Microsoft Advertising SDK for your website, send us an email at aiacare@microsoft.com. In the future, we will provide a way to generate a live ad unit ID.

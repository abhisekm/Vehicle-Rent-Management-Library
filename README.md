# Vehicle-Rent-Management-Library

A simple Vehicle management demo library

## App Navigation Chart
![App Navigation](https://i.ibb.co/pz41vX6/rental-nav.png)

## App setup 

 Add the following
 
 1. Add it in your root build.gradle at the end of repositories:
 ```
 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
 2. Add to your build.gradle file
 ```
 dependencies {
	        implementation 'com.github.abhisekm:Vehicle-Rent-Management-Library:Tag'
	}
 ```
 
 Although I am not performing any api calls. You can further configure the module using
 
 
 ```kotlin
  RentalConfig.setup(Config(api = "custom api endpint"))
 ```

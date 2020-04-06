# PageIndicatorView


How to

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

    gradle
    maven
    sbt
    leiningen

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.nguyenkhiemit:PageIndicatorView:0.1.3'
	}
	
Usage

XML:

<RelativeLayout>
    ...

	<com.nguyen.indicatorview.IndicatorView
        android:id="@+id/indicator"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    ...
</RelativeLayout>

Java:

  indicatorView.setViewPager(viewPager); //make sure that adapter had added to viewpager


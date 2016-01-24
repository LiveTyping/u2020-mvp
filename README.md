U+2020-mvp
======

Port of Jake Wharton's [U2020 sample app][u2020] with use of MVP pattern and [Dagger 2][dagger2].

![Debug drawer](u2020-mvp.gif)

Dagger 2
-------
[Watch the corresponding talk][parleys] or [view the slides][slides].

TODO



MVP
-------
TODO



Testing
-------
[Espresso 2][espresso2] and JUnit4 are used for tests. Activities are instrumented by [ActivityRule][activityrule] (by Jake Wharton).



Android Studio Template
-----------------------
Also we've created a [u2020-mvp Android Studio Template][u2020-mvp-template]
to quickly generate the u2020-mvp classes.


Libraries
-------
 * Dagger 2 - https://github.com/google/dagger
 * ButterKnife - http://jakewharton.github.io/butterknife
 * Retrofit - http://square.github.io/retrofit
 * Moshi - https://github.com/square/moshi
 * Picasso - http://square.github.io/picasso
 * OkHttp - http://square.github.io/okhttp
 * RxJava - https://github.com/ReactiveX/RxJava
 * RxAndroid - https://github.com/ReactiveX/RxAndroid
 * Madge - http://github.com/JakeWharton/madge
 * Timber - http://github.com/JakeWharton/timber
 * ProcessPhoenix - https://github.com/JakeWharton/ProcessPhoenix
 * Telescope - https://github.com/mattprecious/telescope
 * LeakCanary - http://github.com/square/leakcanary
 * Espresso 2 - https://code.google.com/p/android-test-kit/



License
-------

    Copyright 2014 Live Typing

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[u2020]: https://github.com/JakeWharton/u2020
[dagger2]: https://github.com/google/dagger
[espresso2]: https://code.google.com/p/android-test-kit/wiki/EspressoSetupInstructions
[activityrule]: https://gist.github.com/JakeWharton/1c2f2cadab2ddd97f9fb
[parleys]: https://parleys.com/play/5471cdd1e4b065ebcfa1d557/
[slides]: https://speakerdeck.com/jakewharton/dependency-injection-with-dagger-2-devoxx-2014
[u2020-mvp-template]: https://github.com/LiveTyping/u2020-mvp-android-studio-template

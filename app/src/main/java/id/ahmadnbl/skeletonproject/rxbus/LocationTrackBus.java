package id.ahmadnbl.skeletonproject.rxbus;

import android.content.Context;
import android.content.Intent;
import android.location.Location;

import id.ahmadnbl.skeletonproject.services.LocationTrackService;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by billy on 4/5/17.
 */

public class LocationTrackBus {

    private static LocationTrackBus instance;

    private BehaviorSubject<Location> subject = BehaviorSubject.create();

    /**
     * Getting the instance for this bus
     *
     * @return current instance
     */
    public static LocationTrackBus instanceOf() {
        if (instance == null) {
            instance = new LocationTrackBus();
        }
        return instance;
    }


    public void sendCurrentLocation(Location currentLocation) {
        if (subject.hasObservers()) {
            subject.onNext(currentLocation);
        }
    }

    @Nullable
    public Disposable register(Consumer<Location> onNext, final Context context) {
        Disposable disp = subject.subscribe(onNext, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) {
                throwable.printStackTrace();
//                FirebaseCrash.report(throwable);
            }
        });
        context.startService(new Intent(context, LocationTrackService.class));
        return disp;
    }

    public boolean isBusHasReceiver() {
        return subject.hasObservers();
    }

}

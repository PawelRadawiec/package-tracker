package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Bullet;
import com.info.packagetrackerbackend.model.BulletType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BulletService {

    public List<Bullet> getBullets() {
        return Arrays.asList(
                new Bullet(
                        BulletType.START,
                        getHeader(BulletType.START),
                        getContent(BulletType.START),
                        true
                ),
                new Bullet(
                        BulletType.WAREHOUSE,
                        getHeader(BulletType.WAREHOUSE),
                        getContent(BulletType.WAREHOUSE),
                        false
                ),
                new Bullet(
                        BulletType.SORTING_PLANT,
                        getHeader(BulletType.SORTING_PLANT),
                        getContent(BulletType.SORTING_PLANT),
                        false),
                new Bullet(
                        BulletType.TRANSPORT,
                        getHeader(BulletType.TRANSPORT),
                        getContent(BulletType.TRANSPORT),
                        false
                ),
                new Bullet(
                        BulletType.PARCEL_LOCKER,
                        getHeader(BulletType.PARCEL_LOCKER),
                        getContent(BulletType.PARCEL_LOCKER),
                        false
                )
        );
    }

    private String getHeader(BulletType type) {
        switch (type) {
            case START:
                return "Process start";
            case WAREHOUSE:
                return "Warehouse";
            case SORTING_PLANT:
                return "Sorting plant";
            case TRANSPORT:
                return "Transport";
            case PARCEL_LOCKER:
                return "Parcel locker";
            default:
                return null;
        }
    }

    private String getContent(BulletType type) {
        switch (type) {
            case START:
                return "We will take care of your package";
            case WAREHOUSE:
                return "Package in warehouse";
            case SORTING_PLANT:
                return "Package in sorting plant";
            case TRANSPORT:
                return "Package in transport";
            case PARCEL_LOCKER:
                return "Package inside parcel locker";
            default:
                return null;
        }
    }

}

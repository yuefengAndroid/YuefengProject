package com.example.yuefengproject.data.repository

import com.example.yuefengproject.data.entity.StoreCouponEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeRepository {
    lateinit var dbReference: DatabaseReference

    //廣告輪播
    fun getAdBannerData(task: OnActivityBannerFinish) {
        val adBannerList = mutableListOf<StoreCouponEntity>()

        dbReference = FirebaseDatabase.getInstance().getReference("Data")

        dbReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (item in snapshot.child("ActivityBanner").children) {
                        val activityBannerItems = item.key
                        val entity = snapshot.child("ActivityBanner").child(activityBannerItems.toString())
                            .getValue(StoreCouponEntity::class.java)
                        if (entity != null) {
                            adBannerList.add(entity)
                        }
                    }
                    adBannerList.sortedBy { it.num }
                    task.onFinish(adBannerList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    interface OnActivityBannerFinish {
        fun onFinish(storeCouponEntity: MutableList<StoreCouponEntity>)
    }
}
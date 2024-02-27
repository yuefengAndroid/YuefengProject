package com.example.yuefengproject.data.repository

import com.example.yuefengproject.data.entity.MemberInfoEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyRepository {
    lateinit var dbReference: DatabaseReference

    //取得會員資料
    fun getMemberInfo(task: OnMemberInfoFinish, memberId: String) {
        val memberInfoData = mutableListOf<MemberInfoEntity>()

        dbReference = FirebaseDatabase.getInstance().getReference("Member")

        dbReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (item in snapshot.children) {
                        val memberItems = item.key
                        val entity1 = snapshot.child(memberItems.toString()).getValue(MemberInfoEntity::class.java)
                        if (entity1 != null) {
                            if (entity1.mPhone == memberId)
                                memberInfoData.add(entity1)
                        }
                    }
                    task.onFinish(memberInfoData)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                task.onFailed(error.message)
            }

        })
    }


    interface OnMemberInfoFinish {
        fun onFinish(memberInfoEntity: MutableList<MemberInfoEntity>)
        fun onFailed(err: String)
    }
}
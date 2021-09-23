package com.example.eLab_NBIO.models

import com.example.eLab_NBIO.models.sampling.*
import java.util.*

class Tasks {
    companion object {
        var taskList: MutableList<Task> = ArrayList()
        var position: Int = -1
        var sampling1: Sampling1 = Sampling1()
        var sampling2: Sampling2 = Sampling2()
        var sampling3: Sampling3 = Sampling3()
        var sampling4: Sampling4 = Sampling4()
        var sampling5: Sampling5 = Sampling5()
        var sampling6: Sampling6 = Sampling6()
        var sampling7: Sampling7 = Sampling7()
    }
}
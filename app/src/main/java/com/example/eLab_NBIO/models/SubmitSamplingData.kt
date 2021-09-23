package com.example.eLab_NBIO.models

class SubmitSamplingData(samplingBillName: String, samplingData: String, userId: Int) {
    var samplingBillName: String? = samplingBillName
    var samplingData: String? = samplingData
    var userId: Int = userId
}
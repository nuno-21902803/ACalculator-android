package com.example.acalculator

import retrofit2.http.*

data class PostOperationRequest(val expression: String, val result: Double, val timestamp: Long)
data class PostOperationResponse(val message: String)
data class GetOperationsRequest(val uuid: String,val expression: String, val result: Double, val timestamp: Long)
data class DeleteOperationByIdResponse(val message: String)

interface CalculatorService {

    @Headers("apikey: 8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17")

    @POST("operations")
    suspend fun insert(@Body body: PostOperationRequest):
            PostOperationResponse

    @Headers("apikey: 8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17")
    @GET
    suspend fun getAll(): List<GetOperationsRequest>


    @Headers("apikey: 8270435acfead39ccb03e8aafbf37c49359dfbbcac4ef4769ae82c9531da0e17")
    @DELETE
    suspend fun deleteById(@Path("uuid") uuid: String): DeleteOperationByIdResponse

}
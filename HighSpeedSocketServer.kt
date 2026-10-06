package com.example.autofiletransfer

import java.io.File
import java.io.FileInputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import kotlin.concurrent.thread

class HighSpeedSocketServer {

    companion object {
        private const val PORT = 8988
        private const val BUFFER_SIZE = 1024 * 1024 // 1MB buffer optimized for high-speed Wi-Fi 6 pipelines
    }

    // Stream files seamlessly up to 100MB/s
    fun streamFileToReceiver(hostAddress: String, fileToSend: File, progressCallback: (Long) -> Unit) {
        thread {
            val socket = Socket()
            try {
                socket.bind(null)
                socket.connect(InetSocketAddress(hostAddress, PORT), 5000)

                val outputStream: OutputStream = socket.getOutputStream()
                val inputStream = FileInputStream(fileToSend)
                val buffer = ByteArray(BUFFER_SIZE)
                var bytesRead: Int
                var totalBytesSent: Long = 0

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                    totalBytesSent += bytesRead
                    progressCallback(totalBytesSent)
                }

                outputStream.flush()
                inputStream.close()
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (socket.isConnected) {
                    socket.close()
                }
            }
        }
    }
}

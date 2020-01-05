import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import com.example.museumapp.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import model.Artwork
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun toByteArray(drawable: Drawable) : ByteArray {
    val bitmap = drawable.toBitmap()
    val byteStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
    return byteStream.toByteArray()
}

fun toDrawable(byteArray: ByteArray, resources: Resources) : Drawable {
    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    return bitmap.toDrawable(resources)
}

fun shareArtwork(context: Context, artwork: Artwork) {
    val url = getUriFromBitmap(context, toDrawable(artwork.imageData, context.resources).toBitmap())
    Picasso.get().load(url).into(object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, getUriFromBitmap(context, bitmap))
            intent.putExtra(
                Intent.EXTRA_TEXT, "I found this artwork titled \'${artwork.title}\' very interesting. Check it out now using ${context.getString(R.string.app_name)}!")
            context.startActivity(Intent.createChooser(intent, "Share Image"))
        }
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) { }
        override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) { }
    })
}

fun getUriFromBitmap(context: Context, bmp: Bitmap?): Uri? {
    var bmpUri: Uri? = null
    try {
        val file = File(context.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")

        val out = FileOutputStream(file)
        bmp?.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.close()
        bmpUri = Uri.fromFile(file)

    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}
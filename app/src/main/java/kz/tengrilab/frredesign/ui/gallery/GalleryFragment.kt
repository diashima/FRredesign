package kz.tengrilab.frredesign.ui.gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kz.tengrilab.frredesign.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pickImage()

    }

    private fun pickImage(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        val storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/" + "koregen_face_original")
        if (!storageDirectory.exists()){
            storageDirectory.mkdir()
        }
        val selectedUri = Uri.parse(storageDirectory.path)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setDataAndType(selectedUri, "image/*")
        startActivityForResult(intent, PICK_IMAGE_MULTIPLE)
    }

    companion object {
        const val PICK_IMAGE_MULTIPLE = 1
    }
}
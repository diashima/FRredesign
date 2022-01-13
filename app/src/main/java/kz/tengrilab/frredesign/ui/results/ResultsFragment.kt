package kz.tengrilab.frredesign.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kz.tengrilab.frredesign.databinding.FragmentResultsBinding

class ResultsFragment : Fragment() {

    private lateinit var resultsViewModel: ResultsViewModel
    private var _binding: FragmentResultsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resultsViewModel =
            ViewModelProvider(this).get(ResultsViewModel::class.java)

        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
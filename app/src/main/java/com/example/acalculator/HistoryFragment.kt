import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.acalculator.*
import com.example.acalculator.databinding.FragmentHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private lateinit var model: CalculatorModel
    private var adapter = HistoryAdapter(onClick = ::onOperationClick, onLongClick = ::onOperationLongClick)
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        model = CalculatorRoom(CalculatorDatabase.getInstance(requireContext()).operationDao())
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Historico"
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        binding = FragmentHistoryBinding.bind(view)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.rvHistoric.adapter = adapter
        model.getHistory { updateHistory(it) }
    }

    private fun onOperationClick(operation: OperationUI) {
        NavigationManager.goToOperationDetail(parentFragmentManager, operation)
    }

    private fun onOperationLongClick(operation: OperationUI): Boolean {
        Toast.makeText(requireContext(), " Apagando", Toast.LENGTH_SHORT).show()
        model.deleteOperation(operation.uuid) { model.getHistory { updateHistory(it) } }
        return false
    }

    private fun updateHistory(operations: List<OperationUI>) {
        val history = operations.map { OperationUI(it.uuid, it.expression, it.result, it.timeStamp) }
        CoroutineScope(Dispatchers.Main).launch {
            showHistory(history.isNotEmpty())
            adapter.updateItems(history)
        }
    }

    private fun showHistory(show: Boolean) {
        if (show) {
            binding.rvHistoric.visibility = View.VISIBLE
            binding.textNoHistoryAvailable.visibility = View.GONE
        } else {
            binding.rvHistoric.visibility = View.GONE
            binding.textNoHistoryAvailable.visibility = View.VISIBLE
        }
    }

}
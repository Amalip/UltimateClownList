package com.amalip.testapp.application.views

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import com.amalip.testapp.R
import com.amalip.testapp.application.adapters.UserListAdapter
import com.amalip.testapp.application.utils.BaseFragment
import com.amalip.testapp.application.viewInterfaces.IUserList
import com.amalip.testapp.application.viewInterfaces.UserRowEventHandler
import com.amalip.testapp.application.viewModels.UserListViewModel
import com.amalip.testapp.databinding.UserListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : BaseFragment(), IUserList {

    //region Injections
    private val vm: UserListViewModel by viewModel()
    private var adapter: UserListAdapter? = null
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = UserListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = R.layout.user_list_fragment
        setHasOptionsMenu(true)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        (binding as UserListFragmentBinding).vm = vm
        (binding as UserListFragmentBinding).eh = this
    }

    override fun onResume() {
        super.onResume()

        setRecyclerView()
    }
    //endregion

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.principalmenu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        createConfirmationDialog(R.string.deleteMessageAll) { vm.deleteAll() }

        return true
    }

    private fun getListVMBundle(): Bundle {
        val bundle = Bundle()

        bundle.putParcelable("listVM", vm)

        return bundle
    }

    private fun createConfirmationDialog(message: Int, function: () -> Any) {
        val dialog = AlertDialog.Builder(context)

        dialog.setTitle(R.string.delete)
        dialog.setMessage(message)
        dialog.setPositiveButton(R.string.sure) { dialog, _ ->
            function.invoke()
            dialog.dismiss()
        }
        dialog.setNegativeButton(R.string.ohNo) { dialog, _ ->
            dialog.dismiss()
        }
        dialog.create()

        dialog.show()
    }

    override fun onClickGoToAdd() {
        navController?.navigate(R.id.action_userListFragment_to_userFormFragment, getListVMBundle())
    }

    override fun setRecyclerView() {
        (binding as UserListFragmentBinding).rcv.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        (binding as UserListFragmentBinding).rcv.adapter = adapter

        vm.userList.observe(this, {

            setVisibilities(it.count())

            it.forEachIndexed { index, row ->
                row.user.value!!.id = index
                row.eventHandler = object : UserRowEventHandler {
                    override fun onClickRow() {
                        val bundle = getListVMBundle()
                        bundle.putParcelable("selectedUser", row.user.value!!)

                        navController?.navigate(
                            R.id.action_userListFragment_to_userFormFragment,
                            bundle
                        )
                    }

                    override fun onClickDelete() {
                        createConfirmationDialog(R.string.deleteMessage) { vm.deleteUser(index) }
                    }
                }
            }

            adapter!!.submitList(it.toList())
            Handler().postDelayed({
                adapter!!.notifyDataSetChanged()
            }, 50)
        })
    }

    private fun setVisibilities(size: Int) {
        if (size == 0) {
            setMenuVisibility(false)
            vm.showEmpty()
        }
        else {
            setMenuVisibility(true)
            vm.hideEmpty()
        }
    }
}
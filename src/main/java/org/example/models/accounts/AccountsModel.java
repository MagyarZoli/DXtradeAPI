package org.example.models.accounts;

import java.util.List;

public interface AccountsModel {

  List<? extends AccountModel> getAccounts();
}

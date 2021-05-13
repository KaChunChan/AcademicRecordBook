package com.kachunchan.academicrecordbook.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    @Autowired
    private AccountService service;

    @RequestMapping(value="/{username}/account", method= RequestMethod.GET)
    public String getAnAccount(@PathVariable String username, ModelMap model) {
        Account account = service.getAnAccount(username);
        model.addAttribute("account", account);
        System.out.println("The model has: " + model.toString());
        return "account";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void accountNotFoundException(AccountDoesNotExistException ex) {
    }
}

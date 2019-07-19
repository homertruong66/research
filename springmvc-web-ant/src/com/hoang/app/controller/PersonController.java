package com.hoang.app.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.hoang.app.boundary.CountryFC;
import com.hoang.app.boundary.PersonFC;
import com.hoang.app.boundary.ProvinceFC;
import com.hoang.app.command.PersonSearchCommand;
import com.hoang.app.domain.Address;
import com.hoang.app.domain.Country;
import com.hoang.app.domain.Person;
import com.hoang.app.domain.Province;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.dto.PersonDTO;
import com.hoang.app.editor.CountryEditor;
import com.hoang.app.editor.ProvinceEditor;
import com.hoang.app.exception.BusinessException;

/**
 * 
 * @author Hoang Truong
 *
 */

@Controller
@SessionAttributes(value="personDTO")
public class PersonController {

    private final Logger logger = Logger.getLogger(PersonController.class);

    // Requested URIs
    public static final String URI_INDEX    = "/person/index.htm";    
    public static final String URI_CREATE   = "/person/create.htm";
    public static final String URI_UPDATE   = "/person/update.htm";
    public static final String URI_DELETE   = "/person/delete.htm";
    public static final String URI_SUCCESS  = "/person/success.htm";

    // Returned Views
    public static final String VIEW_INDEX   = "person/search";
    public static final String VIEW_CREATE  = "person/edit";
    public static final String VIEW_UPDATE  = "person/edit";
    public static final String VIEW_SUCCESS = "person/success";

    @Autowired
    private DefaultBeanValidator defaultValidator;

    @Autowired
    private PersonFC personFC;

    @Autowired
    private CountryFC countryFC;

    @Autowired
    private ProvinceFC provinceFC;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Country.class, new CountryEditor(countryFC));
        binder.registerCustomEditor(Province.class, new ProvinceEditor(provinceFC));
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.GET)
    public ModelAndView search(ModelMap model) {
        logger.info("search<get>()");

        PersonSearchCommand psc = new PersonSearchCommand();
        model.put("personSearchCommand", psc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.POST)
    public ModelAndView search(ModelMap model,
        @ModelAttribute("personSearchCommand") PersonSearchCommand psc )
    {
        logger.info("search<post>('" + psc.getFirstName() + "')");

        PagedResultDTO<Person> pr = personFC.search(psc.getPage(), psc.getFirstName());
        model.put("pagedResult", pr);
        model.put("createUri", URI_CREATE);
        model.put("updateUri", URI_UPDATE);
        model.put("deleteUri", URI_DELETE);
        model.put("personSearchCommand", psc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.GET)
    public ModelAndView create(ModelMap model) {
        logger.info("creat<get>()");

        Address homeAddress = new Address();
        homeAddress.setCountry(countryFC.get("VIE"));
        homeAddress.setProvince(new Province());

        Address workAddress = new Address();
        workAddress.setCountry(countryFC.get("CAN"));
        workAddress.setProvince(new Province());

        PersonDTO personDTO = new PersonDTO();
        personDTO.setHomeAddress(homeAddress);
        personDTO.setWorkAddress(workAddress);
        model.put("personDTO", personDTO);

        prepareEditData(model, null, null);

        return new ModelAndView(VIEW_CREATE,model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.POST)
    public ModelAndView create(ModelMap model,
        @ModelAttribute("personDTO") PersonDTO personDTO,
        Errors errors)
    {
        logger.info("create<post>('" + personDTO.getFirstName() + "')");

        defaultValidator.validate(personDTO, errors);
        if (errors.hasErrors()) {
            prepareEditData(model,
                            personDTO.getHomeAddress().getCountry().getId(),
                            personDTO.getWorkAddress().getCountry().getId());
            return new ModelAndView (VIEW_CREATE, model);
        }
        personDTO.setFullName(personDTO.getLastName() + ", " + personDTO.getFirstName());
        personFC.create(personDTO);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model, 
        @RequestParam("id") Long id)
    {
        logger.info("update<get>(" + id + ")");

        Person person = personFC.read(id);
        if (person == null) {
            throw new IllegalArgumentException("Person " + id + " not found.");
        }
        PersonDTO personDTO = new PersonDTO();
        personDTO.copyFromPerson(person);
        model.put("personDTO", personDTO);

        prepareEditData(model,
                        person.getHomeAddress().getCountry().getId(),
                        person.getWorkAddress().getCountry().getId());

        return new ModelAndView(VIEW_UPDATE, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("personDTO") PersonDTO personDTO,
        Errors errors)
    {
        logger.info("update<post>(" + personDTO.getId() + ")");

        defaultValidator.validate(personDTO, errors);
        if (errors.hasErrors()) {
            prepareEditData(model,
                            personDTO.getHomeAddress().getCountry().getId(),
                            personDTO.getWorkAddress().getCountry().getId());
            return new ModelAndView(VIEW_UPDATE, model);
        }
        personDTO.setFullName(personDTO.getLastName() + ", " + personDTO.getFirstName());
        personFC.update(personDTO);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam(value="id") Long id   ) throws BusinessException
    {
        logger.info("delete(" + id + ")");
               
        personFC.delete(id);

        return new ModelAndView("redirect:" + URI_INDEX);
    }
   
    // Utility methods
	private void prepareEditData(ModelMap model, 
        Long homeCountryId, Long workCountryId)
    {
        List<Country> countries = countryFC.getAll();
        model.put("countries", countries);

        Country homeCountry;
        if (homeCountryId == null) {
        	homeCountry = countryFC.getWithProvinces("VIE");
        }
        else {
            homeCountry = countryFC.getWithProvinces(homeCountryId);
        }          
        model.put("homeProvinces", homeCountry.getProvinces());

        Country workCountry;
        if (workCountryId == null) {
        	workCountry = countryFC.getWithProvinces("CAN");
        }
        else {
            workCountry = countryFC.getWithProvinces(workCountryId);
        }             
        model.put("workProvinces", workCountry.getProvinces());
    }
}


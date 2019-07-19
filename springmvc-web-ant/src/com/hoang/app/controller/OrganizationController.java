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
import com.hoang.app.boundary.OrganizationFC;
import com.hoang.app.boundary.ProvinceFC;
import com.hoang.app.command.OrganizationSearchCommand;
import com.hoang.app.domain.Address;
import com.hoang.app.domain.Country;
import com.hoang.app.domain.Organization;
import com.hoang.app.domain.Province;
import com.hoang.app.dto.OrganizationDTO;
import com.hoang.app.dto.PagedResultDTO;
import com.hoang.app.editor.CountryEditor;
import com.hoang.app.editor.ProvinceEditor;
import com.hoang.app.exception.BusinessException;

/**
 * 
 * @author Hoang Truong
 *
 */

@Controller
@SessionAttributes("organizationDTO")
public class OrganizationController {

    private final Logger logger = Logger.getLogger(OrganizationController.class);

    // Requested URIs
    public static final String URI_INDEX    = "/organization/index.htm";    
    public static final String URI_CREATE   = "/organization/create.htm";
    public static final String URI_UPDATE   = "/organization/update.htm";
    public static final String URI_DELETE   = "/organization/delete.htm";
    public static final String URI_SUCCESS  = "/organization/success.htm";    

    // Returned Views
    public static final String VIEW_INDEX   = "organization/search";
    public static final String VIEW_CREATE  = "organization/edit";
    public static final String VIEW_UPDATE  = "organization/edit";
    public static final String VIEW_SUCCESS = "organization/success";    

    @Autowired
    private DefaultBeanValidator defaultValidator;

    @Autowired
    private OrganizationFC organizationFC;

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
    public ModelAndView search(ModelMap model)   {
        logger.info("search<get>()");

        OrganizationSearchCommand osc = new OrganizationSearchCommand();
        model.put("organizationSearchCommand", osc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_INDEX, method=RequestMethod.POST)
    public ModelAndView search(ModelMap model,
        @ModelAttribute("organizationSearchCommand") OrganizationSearchCommand osc )
    {
        logger.info("search<post>('" + osc.getName() + "')");

        PagedResultDTO<Organization> pr = organizationFC.search(osc.getPage(), osc.getName());
        model.put("pagedResult", pr);
        model.put("createUri", URI_CREATE);
        model.put("updateUri", URI_UPDATE);
        model.put("deleteUri", URI_DELETE);
        model.put("organizationSearchCommand", osc);

        return new ModelAndView(VIEW_INDEX, model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.GET)
    public ModelAndView create(ModelMap model) {
        logger.info("create<get>()");

        Address address = new Address();
        address.setCountry(countryFC.get("CAN"));
        address.setProvince(new Province());

        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setAddress(address);
        model.put("organizationDTO", organizationDTO);

        prepareEditData(model, null);

        return new ModelAndView(VIEW_CREATE,model);
    }

    @RequestMapping(value=URI_CREATE, method=RequestMethod.POST)
    public ModelAndView create(ModelMap model,
        @ModelAttribute("organizationDTO") OrganizationDTO organizationDTO,
        Errors errors)
    {
        logger.info("create<post>('" + organizationDTO.getName() + "')");

        defaultValidator.validate(organizationDTO, errors);
        if (errors.hasErrors()) {
            prepareEditData(model, organizationDTO.getAddress().getCountry().getId());
            return new ModelAndView (VIEW_CREATE, model);
        }
        organizationFC.create(organizationDTO);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.GET)
    public ModelAndView update(ModelMap model, 
        @RequestParam("id") Long id)
    {
        logger.info("update<get>(" + id + ")");

        Organization org = organizationFC.read(id);
        if (org == null) {
            throw new IllegalArgumentException("Organization " + id + " not found.");
        }
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.copyFromOrganization(org);
        model.put("organizationDTO", organizationDTO);

        prepareEditData(model, org.getAddress().getCountry().getId());

        return new ModelAndView(VIEW_UPDATE, model);
    }

    @RequestMapping(value=URI_UPDATE, method=RequestMethod.POST)
    public ModelAndView update(ModelMap model,
        @ModelAttribute("organizationDTO") OrganizationDTO organizationDTO,
        Errors errors)
    {
        logger.info("update<post>(" + organizationDTO.getId() + ")");

        defaultValidator.validate(organizationDTO, errors);
        if (errors.hasErrors()) {
            prepareEditData(model, organizationDTO.getAddress().getCountry().getId());
            return new ModelAndView(VIEW_UPDATE, model);
        }
        organizationFC.update(organizationDTO);

        return new ModelAndView(VIEW_SUCCESS);
    }

    @RequestMapping(value=URI_DELETE)
    public ModelAndView delete(ModelMap model,
        @RequestParam(value="id") Long id   ) throws BusinessException
    {
        logger.info("delete(" + id + ")");
             
        organizationFC.delete(id);

        return new ModelAndView("redirect:" + URI_INDEX);
    }


    // Utility Methods
	private void prepareEditData(ModelMap model, Long countryId) {
        List<Country> countries = countryFC.getAll();
        model.put("countries", countries);

        Country country;
        if (countryId == null) {
        	country = countryFC.getWithProvinces("CAN");
        }
        else {
            country = countryFC.getWithProvinces(countryId);
        }        
        model.put("provinces", country.getProvinces());
    }
}

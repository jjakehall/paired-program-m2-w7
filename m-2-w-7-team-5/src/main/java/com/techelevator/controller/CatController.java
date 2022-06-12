package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CatCard> list(){
        return catCardDao.list();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CatCard id(@PathVariable int id) {
        return catCardDao.get(id);
    }

    @GetMapping("random")
    public CatCard getRandom()
    {
        CatCard catCard = new CatCard();
        // use the API service to get a random cat picture
        CatPic picture = catPicService.getPic();
        CatFact fact = catFactService.getFact();

        String imgUrl = picture.getFile();
        String factText = fact.getText();

        catCard.setImgUrl(imgUrl);
        catCard.setCatFact(factText);

        return catCard;
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean save(@Valid @RequestBody CatCard catCard) {
        return catCardDao.save(catCard);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public boolean update(@RequestBody CatCard catCard, @PathVariable int id) {
        return catCardDao.update(id,catCard);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable int id) {
       return catCardDao.delete(id);
    }



}

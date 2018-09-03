package io.kanteen.service.impl;

import io.kanteen.dto.*;
import io.kanteen.persistance.entity.Account;
import io.kanteen.persistance.entity.Meal;
import io.kanteen.persistance.entity.Parent;
import io.kanteen.persistance.repository.IAccountRepository;
import io.kanteen.persistance.repository.IParentRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MealServiceTest {

    @Autowired
    MealService mealService;
    @Autowired
    ChildService childService;
    @Autowired
    ParentService parentService;
    @Autowired
    AccountService accountService;
    @Autowired
    IParentRepository parentRepository;
    @Autowired
    IAccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    ChildDto childDto;
    ChildDto childDto2;
    ChildDto childAvecId;
    ChildDto childAvecId2;
    ParentDtoFull parentDto;
//    ParentDtoFull parentDtoAvecId;
    String dateString;
    String dateString2;

    @org.junit.Before
    public void setUp() throws Exception {

        // sans id car pas persistes
        childDto = new ChildDto("Dina", "CM2");
        childDto2 = new ChildDto("Tom", "CM2");
        parentDto = new ParentDtoFull("Jacques","fef@fref.erf");


        // avec id car persistes avec save

//        parentDto = parentService.saveParentWithChildId(parentDto,childAvecId.getId());
//        parentService.saveParentWithChildId(parentDto,childAvecId2.getId());
        childAvecId = childService.saveChild(childDto);
        childAvecId2 = childService.saveChild(childDto2);
        parentDto = parentService.saveParentWithChildId(parentDto,childAvecId.getId());
        parentDto = parentService.saveParentWithChildId(parentDto,childAvecId2.getId());
        dateString = "2018-08-30";
        dateString2 = "2018-08-25";
    }

    @org.junit.After
    public void tearDown() throws Exception {
        childService.deleteChildren(childAvecId.getId());
        childService.deleteChildren(childAvecId2.getId());

        Optional<Parent> parent = parentRepository.findById(parentDto.getId());
        System.out.println(parentDto.getId());
        if(parent.isPresent()){
            parentService.deleteParent(parentDto.getAccount().getId());
        }

        accountService.deleteAccount(parentDto.getAccount().getId());
//        parentService.deleteParent(parentDto.getId());
        List<Account> acc = accountRepository.findAll();
        if(acc.size()>=1){
            for(Account a:acc){
                accountService.deleteAccount(a.getId());
            }
        }
    }

    @Test
    public void getAllMeals() {

        MealDto m1 = mealService.saveMealNoDto(childAvecId.getId(), dateString);
        MealDto m2 = mealService.saveMealNoDto(childAvecId2.getId(), dateString);

        //assert get
        List<MealDto> allMeals = mealService.getAllMeals();
        assertTrue(allMeals.size() > 0);

        mealService.deleteMealById(m1.getId());
        mealService.deleteMealById(m2.getId());

    }


    @Test
    public void saveMealNoDto() {

        MealDto m1 = mealService.saveMealNoDto(childAvecId.getId(), dateString);
        MealDto m2 = mealService.saveMealNoDto(childAvecId2.getId(), dateString);

        mealService.deleteMealById(m1.getId());
        mealService.deleteMealById(m2.getId());


    }

    @Test
    public void deleteMealById() {

        MealDto m1 = mealService.saveMealNoDto(childAvecId.getId(), dateString);
        MealDto m2 = mealService.saveMealNoDto(childAvecId2.getId(), dateString);

        mealService.deleteMealById(m1.getId());
        mealService.deleteMealById(m2.getId());
        assertTrue(mealService.getAllMeals().size() == 0);

    }

    @Test
    public void getMealsByDay() {

        String dateTest = "2018-05-25";
        String dateTest2 = "2018-05-24";

        MealDto m1 = mealService.saveMealNoDto(childAvecId.getId(), dateTest);
        MealDto m2 = mealService.saveMealNoDto(childAvecId2.getId(), dateTest);
        MealDto m3 = mealService.saveMealNoDto(childAvecId2.getId(), dateTest2);

        List<MealDto> listMeals = mealService.getMealsByDay(dateTest);
        assertTrue(listMeals.size()==2);

        //sup les meals
        mealService.deleteMealById(m1.getId());
        mealService.deleteMealById(m2.getId());
        mealService.deleteMealById(m3.getId());
    }


    @Test
    public void getMealsByParentId(){
        MealDto m1 = mealService.saveMealNoDto(childAvecId.getId(), dateString);
        MealDto m2 = mealService.saveMealNoDto(childAvecId2.getId(), dateString);
        MealDto m3 = mealService.saveMealNoDto(childAvecId2.getId(), dateString2);

        List<MealDto> meals = mealService.getMealsByParentId(parentDto.getId());
        assertTrue(meals.size()==3);

        mealService.deleteMealById(m1.getId());
        mealService.deleteMealById(m2.getId());
        mealService.deleteMealById(m3.getId());

        parentService.deleteParent(parentDto.getId());
    }
    @Test
    public void getMealsByChildId(){
        MealDto m1 = mealService.saveMealNoDto(childAvecId.getId(), dateString);
        MealDto m2 = mealService.saveMealNoDto(childAvecId.getId(), dateString2);

        List<MealDto> meals = mealService.getMealsByChildId(childAvecId.getId());
//        System.out.println("----"+meals.get(0).getId());
        assertTrue(meals.size()==2);
        mealService.deleteMealById(m1.getId());
        mealService.deleteMealById(m2.getId());

    }

}
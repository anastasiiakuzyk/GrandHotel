document.addEventListener('DOMContentLoaded', function () {
    let AllData = reqData;

    const listEl = document.getElementById('option_select_list');
    const optionListEl = document.getElementById('option__list');
    const hiddenSelect = document.getElementById("options");

    const optionInputEl = document.getElementById('option');
    optionInputEl.addEventListener('click', function (e){
        const selectOptionEl = document.getElementById('option__list');
        selectOptionEl.classList.add('opened');
        e.stopPropagation();
    });
    window.addEventListener('click', function () {
        const selectOptionEl = document.getElementById('option__list');
        selectOptionEl.classList.remove('opened');
    });
    optionInputEl.addEventListener('input', (e) => {
        AllData = reqData.filter((val) => val.name.includes(e.target.value));
        render(listEl, data, optionListEl, AllData);
    });

    const optionsOnRemove = (inx) => {
        data.splice(inx, 1);
        render(listEl, data, optionListEl, AllData);
    };

    const optionsOnAdd = (id) => {
        data.push(id);
        render(listEl, data, optionListEl, AllData);
    };

    const getNotUsedData = (allData, data) => allData.filter(val => !data.includes(val.id));

    const render = (listEl, data, optionListEl, allData) => {
        hiddenSelect.innerHTML = data.map((val) => `<option selected="selected" value="${val}"></option>`).join('');
        optionListEl.innerHTML = getNotUsedData(allData, data).map(val => `<div role="button" class="expanded__list__item"><div class="expanded__list__item__name">${val.name}</div></div>`).join('');
        const selectOptionEl = document.getElementsByClassName('expanded__list__item');
        Array.from(selectOptionEl).forEach((el, inx) => el.addEventListener('click', () => optionsOnAdd(getNotUsedData(allData, data)[inx].id)));

        listEl.innerHTML = data.map(val => `<div class="option__list__item">${AllData.find((vadal) => vadal.id === val).name || ''}<div class="option__list__item__close"><span role="button" class="iconify" data-icon="carbon:close" style="color: #FFF;" data-width="22" data-height="22"></span></div></div>`).join('');
        const optionsEl = document.getElementsByClassName('option__list__item');
        Array.from(optionsEl).forEach((el, inx) => el.addEventListener('click', () => optionsOnRemove(inx)));
    };
    render(listEl, data, optionListEl, AllData);
});
'use strict';
document.addEventListener('DOMContentLoaded', function () {
    const switchButtonEl = document.getElementById('add__new__guest');
    const toggleableEl = document.getElementById('toggleable');
    let toggled = true;
    switchButtonEl.addEventListener('click', function (e){
        if (toggled) {
            toggleableEl.classList.add('new__user');
            toggleableEl.innerHTML = '<h4>New guest:</h4>\n' +
                '                        <div class="guest__block__content">\n' +
                '                            <div style="grid-column: 1/3">\n' +
                '                                <label for="guest__phone">Name:</label><br />\n' +
                '                                <input type="text" id="guest__phone" placeholder="Enter guest name">\n' +
                '                            </div>\n' +
                '                            <div>\n' +
                '                                <label for="guest__phone">Telephone:</label><br />\n' +
                '                                <input type="text" id="guest__phone" placeholder="Enter phone">\n' +
                '                            </div>\n' +
                '                            <div>\n' +
                '                                <label for="guest__phone">Passport:</label><br />\n' +
                '                                <input type="number" placeholder="Telephone">\n' +
                '                            </div>\n' +
                '\n' +
                '                        </div>\n' +
                '                        <div class="guest__block__content">\n' +
                '                            <label for="guest__phone" style="grid-column: 1/3;">Personal data:</label>\n' +
                '                            <div class="guest__block__content__data" style="grid-column: 1/3;">\n' +
                '                                Курит, Пьет, не бьет детей.\n' +
                '                            </div>\n' +
                '                        </div>';
            switchButtonEl.innerText = 'Choose existing';
        } else {
            toggleableEl.classList.remove('new__user');
            switchButtonEl.innerText = 'Add new guest';
            toggleableEl.innerHTML = '<label for="guest">Guest:</label><br />\n' +
                '                        <input type="text" id="guest" placeholder="Start typing some data about guest">\n' +
                '                        <div id="guest__list" class="expanded__list">\n' +
                '                            <div class="expanded__list__item">\n' +
                '                                <div class="expanded__list__item__name">\n' +
                '                                    Aton Monkov\n' +
                '                                </div>\n' +
                '                                <div class="expanded__list__item__doc">000678465</div>\n' +
                '                            </div>\n' +
                '                            <div class="expanded__list__item">\n' +
                '                                <div class="expanded__list__item__name">\n' +
                '                                    Anton Monkov\n' +
                '                                </div>\n' +
                '                                <div class="expanded__list__item__doc">000678465</div>\n' +
                '                            </div>\n' +
                '                            <div class="expanded__list__item">\n' +
                '                                <div class="expanded__list__item__name">\n' +
                '                                    Anton Monkov\n' +
                '                                </div>\n' +
                '                                <div class="expanded__list__item__doc">000678465</div>\n' +
                '                            </div>\n' +
                '                            <div class="expanded__list__item">\n' +
                '                                <div class="expanded__list__item__name">\n' +
                '                                    Anton Monkov\n' +
                '                                </div>\n' +
                '                                <div class="expanded__list__item__doc">000678465</div>\n' +
                '                            </div>\n' +
                '                            <div class="expanded__list__item">\n' +
                '                                <div class="expanded__list__item__name">\n' +
                '                                    Anton Monkov\n' +
                '                                </div>\n' +
                '                                <div class="expanded__list__item__doc">000678465</div>\n' +
                '                            </div>\n' +
                '                            <div class="expanded__list__item">\n' +
                '                                <div class="expanded__list__item__name">\n' +
                '                                    Anton Monkov\n' +
                '                                </div>\n' +
                '                                <div class="expanded__list__item__doc">000678465</div>\n' +
                '                            </div>\n' +
                '                        </div>';
        }
        toggled = !toggled;
    });
});
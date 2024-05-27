HTMLElement.INVALID_CLASS_NAME = '-invalid';
HTMLElement.VISIBLE_CLASS_NAME = '-visible';

HTMLElement.prototype.show = function () {
    this.classList.add(HTMLElement.VISIBLE_CLASS_NAME);
    return this;
}

HTMLElement.prototype.hide = function () {
    this.classList.remove(HTMLElement.VISIBLE_CLASS_NAME);
    return this;
}

HTMLElement.prototype.block = function () {
    this.style.display = 'block';
    return this;
}
HTMLElement.prototype.none = function () {
    this.style.display = 'none';
    return this;
}

HTMLElement.prototype.disable = function () {
    this.setAttribute('disabled', '');
    return this;
}

HTMLElement.prototype.enable = function () {
    this.removeAttribute('disabled');
    return this;
}



const cover = document.getElementById('cover');

cover.show = function (onclick) {
    this.onclick = onclick;
    this.classList.add(HTMLElement.VISIBLE_CLASS_NAME);
    return this;
}


document.querySelectorAll('[rel="cancelButton"]').forEach(el => {
    el.onclick = function () {
        if (el.classList.contains('umBtn')) {
            cover.hide();
            userModal.hide();
            userModalIndex = 1;
            return;
        }
        if (el.classList.contains('pmBtn')) {
            cover.hide();
            paymentModal.hide();
            currentPaymentModalIndex = 1;
            return;
        }
    }}
);

class DialogObj {
    static cover = null;

    element;

    constructor(params) {
        console.log(params)
        if (DialogObj.cover === null) {
            const cover = document.createElement('div');
            cover.classList.add('_obj-dialog-cover');
            document.body.prepend(cover);
            DialogObj.cover = cover;
        }
        params.buttons ??= [];
        const element = new DOMParser().parseFromString(`
            <div class="_obj-dialog">
                <div class="__title">${params.title}</div>
                <div class="__content">${params.content}</div>
            </div>`, 'text/html').querySelector('._obj-dialog');
        if (params.buttons.length > 0) {
            const buttonContainer = document.createElement('div');
            buttonContainer.classList.add('__button-container');
            buttonContainer.style.gridTemplateColumns = `repeat(${params.buttons.length}, minmax(0, 1fr))`;
            for (const buttonObject of params.buttons) {
                const button = document.createElement('button');
                button.classList.add('__button');
                button.setAttribute('type', 'button');
                button.innerText = buttonObject.text;
                if (typeof buttonObject.onclick === 'function') {
                    button.onclick = () => {
                        buttonObject.onclick(this);
                    };
                }
                buttonContainer.append(button);
            }
            element.append(buttonContainer);
        }
        document.body.prepend(element);
        this.element = element;
    }

    static createButton(text, onclick) {
        return {text: text, onclick: onclick};
    }

    static createSimpleOk(title, content, onclick) {
        return new DialogObj({
            title: title,
            content: content,
            buttons: [{
                text: '확인',
                onclick: (instance) => {
                    instance.hide();
                    if (typeof onclick === 'function') {
                        onclick(instance);
                    }
                }
            }]
        });
    }

    hide() {
        setTimeout(() => {
            DialogObj.cover.hide();
            this.element.hide();
        }, 100);
        return this;
    }



    show() {
        setTimeout(() => {
            console.log(this.element)
            DialogObj.cover.show();
            this.element.show();
        }, 100);
        return this;
    }
}







